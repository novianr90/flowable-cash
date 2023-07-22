package id.novian.flowablecash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.flowablecash.data.local.models.Accounts
import io.reactivex.rxjava3.core.Observable

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccountsToLocal(query: Accounts)

    @Query("DELETE FROM Accounts WHERE account_id = :id")
    fun deleteAccount(id: Int)

    @Query("DELETE FROM Accounts")
    fun deleteAllAccountsOnLocal()

    @Query("SELECT * FROM Accounts WHERE account_month = :month")
    fun getAllAccounts(month: Int): Observable<List<Accounts>>

    @Query("SELECT * FROM Accounts WHERE account_name = :accountName AND account_month = :month")
    fun getAccountByAccountName(accountName: String, month: Int): Observable<Accounts>

    @Query(
        """
        UPDATE Accounts
        SET account_balance = :value
        WHERE account_name = :accountName
        AND account_month = :month
    """
    )
    fun updateBalanceByAccountName(accountName: String, value: String, month: Int)
}