package id.novian.flowablecash.domain.repository

import android.util.Log
import com.google.gson.Gson
import id.novian.flowablecash.data.local.models.Accounts
import id.novian.flowablecash.data.local.repository.AccountLocalRepository
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.helpers.Mapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

interface AccountsRepository {
    fun recordNewAccounts(
        month: Int,
        accountName: String,
        balance: AccountBalance
    ): Completable

    fun getAllAccounts(month: Int): Maybe<List<AccountDomain>>

    fun getAccountByAccountName(accountName: String, month: Int): Maybe<AccountDomain>

    fun updateBalance(
        balance: AccountBalance,
        accountName: String,
        month: Int,
    ): Completable

    fun updateSpecialBalance(
        balance: AccountBalance,
        accountName: String,
        month: Int,
    ): Completable

    fun getAllSpecific(accountName: String): Observable<List<AccountDomain>>
}

class AccountsRepositoryImpl(
    private val local: AccountLocalRepository,
    private val remote: MainRemoteRepository,
    private val remoteMapper: Mapper<BalanceSheet, AccountDomain>,
    private val localMapper: Mapper<Accounts, AccountDomain>,
    private val gson: Gson
): AccountsRepository {
    override fun recordNewAccounts(
        month: Int,
        accountName: String,
        balance: AccountBalance
    ): Completable {
        return remote.recordNewAccounts(accountName = accountName, balance = balance, month = month)
    }

    override fun getAllAccounts(month: Int): Maybe<List<AccountDomain>> {
        return remote.getAllAccounts(month)
            .flatMap {
                val mapped = it.balanceSheet.map { data ->
                    remoteMapper.mapToDomain(data)
                }
                Maybe.just(mapped)
            }
            .doAfterSuccess {
                val list = it.map { new ->
                    localMapper.mapToModel(new)
                }
                list.forEach { new -> local.insertAccountsToLocal(new) }
            }
            .onErrorResumeNext {
                it.printStackTrace()
                Log.d("AccountRepo", "Error: ${it.message}")
                local.getAllAccountsOnLocal(month)
                    .map { list ->
                        list.map { data ->
                            localMapper.mapToDomain(data)
                        }
                    }
                    .firstElement()
            }
    }

    override fun getAccountByAccountName(
        accountName: String,
        month: Int
    ): Maybe<AccountDomain> {
        return remote.getAccount(accountName = accountName, month = month)
            .flatMap { list ->
                val mapped = remoteMapper.mapToDomain(list.accounts)
                Maybe.just(mapped)
            }
            .onErrorResumeNext {
                it.printStackTrace()
                Log.d("AccountsRepo", "Error: ${it.message}")
                local.getAccountByAccountNameOnLocal(accountName = accountName, month = month)
                    .map { data -> localMapper.mapToDomain(data) }
                    .firstElement()
            }
    }

    override fun updateBalance(
        balance: AccountBalance,
        accountName: String,
        month: Int,
    ): Completable {
        return remote.updateAccount(
            id = null,
            accountName = accountName,
            balance = balance,
            month = month
        )
            .doOnError {
                it.printStackTrace()
                Log.d("AccountsRepo", "Error: ${it.message}")
            }
            .doOnComplete {
                local.updateBalanceByAccountNameOnLocal(accountName = accountName, value = gson.toJson(balance), month)
            }
    }

    override fun updateSpecialBalance(
        balance: AccountBalance,
        accountName: String,
        month: Int
    ): Completable {
       return remote.updateSpecialAccount(
            id = null,
            accountName = accountName,
            balance = balance,
            month = month
       )
           .doOnError {
               it.printStackTrace()
               Log.d("AccountsRepo", "Error: ${it.message}")
           }
           .doOnComplete {
               local.updateBalanceByAccountNameOnLocal(
                   accountName = accountName,
                   value = gson.toJson(balance),
                   month
               )
           }
    }

    override fun getAllSpecific(accountName: String): Observable<List<AccountDomain>> {
        return remote.getSpecificAccounts(accountName)
            .flatMap {
                val mapped = it.balanceSheet.map { data ->
                    remoteMapper.mapToDomain(data)
                }
                Observable.just(mapped)
            }
    }

}