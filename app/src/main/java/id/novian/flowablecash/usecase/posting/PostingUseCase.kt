package id.novian.flowablecash.usecase.posting

import com.google.gson.Gson
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.models.UpdateModelBalanceSheet
import id.novian.flowablecash.data.local.repository.BalanceSheetLocalRepository
import id.novian.flowablecash.data.local.repository.UpdateModelBalanceSheetLocalRepository
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.domain.repository.BalanceSheetRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface PostingUseCase {
    // Create to local for update balance sheet
    fun insertUpdateBalanceSheetToStorage(query: UpdateModelBalanceSheet): Completable

    // Update for updating one particular field on models field (alreadyUpdated)
    fun setUpdateBalanceSheet(accountNameDebit: String, accountNameCredit: String): Observable<Unit>

    // Update to Remote with new data (local and remote additional)
    fun updateBalanceSheetToRemote(accountName: String, balance: AccountBalance): Observable<Unit>

    // Local for update
    fun getUpdateModelBalanceSheetDebit(accountNameDebit: String): Observable<UpdateModelBalanceSheet>
    fun getUpdateBalanceSheetCredit(accountNameCredit: String): Observable<UpdateModelBalanceSheet>

    // Remote to get current balance sheet for update back to remote
    fun getBalanceSheetCurrentOnRemote(accountName: String): Observable<BalanceSheetDomain>

    // (Opt) Delete specific account name based on debit or credit
    fun deleteSpecificDebitAccountName(accountNameDebit: String): Observable<Unit>
    fun deleteSpecificCreditAccountName(accountNameCredit: String): Observable<Unit>
}

class PostingUseCaseImpl(
    private val local: UpdateModelBalanceSheetLocalRepository,
    private val remote: BalanceSheetRepository,
    private val gson: Gson
): PostingUseCase {
    override fun insertUpdateBalanceSheetToStorage(query: UpdateModelBalanceSheet): Completable {
        return Completable.fromAction {
            local.insertNewQueryForUpdateBalanceSheet(query)
        }
    }

    override fun setUpdateBalanceSheet(
        accountNameDebit: String,
        accountNameCredit: String
    ): Observable<Unit> {
        return local.setUpdateBalanceSheet(accountNameDebit = accountNameDebit, accountNameCredit = accountNameCredit)
    }

    override fun updateBalanceSheetToRemote(
        accountName: String,
        balance: AccountBalance
    ): Observable<Unit> {
        return remote.updateBalanceSheet(accountName = accountName, balance = balance)
            .map {  }
    }

    override fun getUpdateModelBalanceSheetDebit(accountNameDebit: String): Observable<UpdateModelBalanceSheet> {
        return local.getDebitAccountNameForUpdate(accountNameDebit)
    }

    override fun getUpdateBalanceSheetCredit(accountNameCredit: String): Observable<UpdateModelBalanceSheet> {
        return local.getCreditAccountNameForUpdate(accountNameCredit)
    }

    override fun getBalanceSheetCurrentOnRemote(accountName: String): Observable<BalanceSheetDomain> {
        return remote.getBalanceSheetByAccountName(accountName = accountName)
    }

    override fun deleteSpecificDebitAccountName(accountNameDebit: String): Observable<Unit> {
        return local.deleteSpecificAccountDebit(accountNameDebit)
    }

    override fun deleteSpecificCreditAccountName(accountNameCredit: String): Observable<Unit> {
        return local.deleteSpecificAccountCredit(accountNameCredit)
    }

}