package id.novian.flowablecash.data.local.repository

import id.novian.flowablecash.data.local.dao.AccountDao
import id.novian.flowablecash.data.local.models.Accounts
import io.reactivex.rxjava3.core.Observable

interface AccountLocalRepository {
    fun insertAccountsToLocal(query: Accounts)

    fun deleteSpecificAccountById(id: Int)

    fun deleteAllAccounts()

    fun getAllAccountsOnLocal(month: Int): Observable<List<Accounts>>

    fun getAccountByAccountNameOnLocal(accountName: String, month: Int): Observable<Accounts>

    fun updateBalanceByAccountNameOnLocal(accountName: String, value: String, month: Int)
}

class AccountLocalRepositoryImpl(
    private val dao: AccountDao
) : AccountLocalRepository {
    override fun insertAccountsToLocal(query: Accounts) {
        return dao.insertAccountsToLocal(query)
    }

    override fun deleteSpecificAccountById(id: Int) {
        return dao.deleteAccount(id)
    }

    override fun deleteAllAccounts() {
        return dao.deleteAllAccountsOnLocal()
    }

    override fun getAllAccountsOnLocal(month: Int): Observable<List<Accounts>> {
        return dao.getAllAccounts(month)
    }

    override fun getAccountByAccountNameOnLocal(
        accountName: String,
        month: Int
    ): Observable<Accounts> {
        return dao.getAccountByAccountName(accountName = accountName, month = month)
    }

    override fun updateBalanceByAccountNameOnLocal(accountName: String, value: String, month: Int) {
        return dao.updateBalanceByAccountName(accountName = accountName, value = value, month = month)
    }


}