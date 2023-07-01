package id.novian.flowablecash.domain.repository

import android.util.Log
import com.google.gson.Gson
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.repository.BalanceSheetLocalRepository
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.helpers.Mapper
import io.reactivex.rxjava3.core.Observable

interface BalanceSheetRepository {
    fun getBalanceSheet(): Observable<List<BalanceSheetDomain>>

    fun getBalanceSheetByAccountName(accountName: String): Observable<BalanceSheetDomain>

    fun updateBalanceSheet(
        balance: AccountBalance,
        accountName: String
    ): Observable<Unit>
}

class BalanceSheetRepositoryImpl(
    private val local: BalanceSheetLocalRepository,
    private val remote: MainRemoteRepository,
    private val remoteMapper: Mapper<BalanceSheet, BalanceSheetDomain>,
    private val localMapper: Mapper<BalanceSheetLocal, BalanceSheetDomain>,
    private val gson: Gson
): BalanceSheetRepository {
    override fun getBalanceSheet(): Observable<List<BalanceSheetDomain>> {
        return remote.getBalanceSheet()
            .map { data -> data.balanceSheet.map { value -> remoteMapper.mapToDomain(value) } }
            .doOnNext { data ->
                data.map { local.insertBalanceSheetToLocal(localMapper.mapToModel(it)) }
            }
            .onErrorResumeNext { _ ->
                local.getBalanceSheet()
                    .map { data -> data.map { localMapper.mapToDomain(it) } }
            }
    }

    override fun getBalanceSheetByAccountName(accountName: String): Observable<BalanceSheetDomain> {
        return remote.getBalanceSheet(accountName)
            .map { data -> remoteMapper.mapToDomain(data) }
            .doOnNext { data ->
                local.insertBalanceSheetToLocal(localMapper.mapToModel(data))
            }
            .onErrorResumeNext {
                local.getBalanceSheet(accountName)
                    .map { data -> localMapper.mapToDomain(data) }
            }
    }

    override fun updateBalanceSheet(
        balance: AccountBalance,
        accountName: String
    ): Observable<Unit> {
        val balanceJson = gson.toJson(balance)
        return remote.updateBalanceSheet(accountName, balanceJson)
            .doOnError { Log.d("REPO", "KONTOL") }
            .doOnNext {
                local.updateBalanceSheetByAccountName(accountName, balanceJson)
            }
    }

}