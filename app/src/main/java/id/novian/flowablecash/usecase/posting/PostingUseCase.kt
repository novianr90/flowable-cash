package id.novian.flowablecash.usecase.posting

import android.util.Log
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.data.remote.models.posting.Postings
import id.novian.flowablecash.data.remote.repository.PostingRepository
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.domain.repository.AccountsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface PostingUseCase {
    // Update to Remote with new data (local and remote additional)
    fun updateAccountsToRemote(accountName: String, balance: AccountBalance, month: Int): Completable

    // Update Special Account to remote (override data on remote)
    fun updateSpecialAccountsToRemote(accountName: String, balance: AccountBalance, month: Int): Completable

    // Remote to get current balance sheet for update back to remote
    fun getAccounts(accountName: String, month: Int): Maybe<AccountDomain>

    // Get Tracker
    fun getTracker(trxId: Int, accId: Int): Maybe<Postings>

    // Record Tracker
    fun recordNewPostingAct(trxId: Int, accId: Int): Completable
}

class PostingUseCaseImpl(
    private val remote: AccountsRepository,
    private val postings: PostingRepository
): PostingUseCase {
    override fun updateAccountsToRemote(accountName: String, balance: AccountBalance, month: Int): Completable {
        return remote.updateBalance(accountName = accountName, balance = balance, month = month)
            .doOnSubscribe { Log.i("UpdateRe", "Subsc") }
            .doOnComplete { Log.i("UpdateRe", "Comple") }
            .doOnError { Log.i("UpdateRe", "Error with ${it.message}") }
    }

    override fun updateSpecialAccountsToRemote(
        accountName: String,
        balance: AccountBalance,
        month: Int
    ): Completable {
       return remote.updateSpecialBalance(accountName = accountName, balance = balance, month = month)
    }

    override fun getAccounts(accountName: String, month: Int): Maybe<AccountDomain> {
        return remote.getAccountByAccountName(accountName, month = month)
            .doOnSubscribe { Log.i("GetAcc", "Subsc") }
            .doOnComplete { Log.i("GetAcc", "Comple") }
            .doOnError { Log.i("GetAcc", "Error with ${it.message}") }
    }

    override fun getTracker(trxId: Int, accId: Int): Maybe<Postings> {
        return postings.getPostingByTrxIdAndAccId(trxId, accId)
            .doOnSubscribe { Log.i("GetTrac", "Subsc") }
            .doOnComplete { Log.i("GetTrac", "Comple") }
            .doOnError { Log.i("GetTrac", "Error with ${it.message}") }
    }

    override fun recordNewPostingAct(trxId: Int, accId: Int): Completable {
        Log.i("PostingTracker", "Invoked")
        return postings.recordNewPosting(trxId, accId)
            .doOnSubscribe { Log.i("PostingTracker", "Subsc") }
            .doOnComplete { Log.i("PostingTracker", "Comple") }
            .doOnError { Log.i("PostingTracker", "Error with ${it.message}") }
    }
}