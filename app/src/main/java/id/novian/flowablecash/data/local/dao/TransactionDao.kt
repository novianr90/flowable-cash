package id.novian.flowablecash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.novian.flowablecash.data.local.models.TransactionLocal
import io.reactivex.rxjava3.core.Observable

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(query: TransactionLocal)

    @Update
    fun updateTransaction(query: TransactionLocal)

    @Query("DELETE FROM `transactionlocal` WHERE id = :id")
    fun deleteTransaction(id: Int)

    @Query("SELECT * FROM TransactionLocal")
    fun getAllTransactions(): Observable<List<TransactionLocal>>

    @Query("SELECT * FROM `transactionlocal` WHERE type =  :type")
    fun getAllTypeTransactions(type: String): Observable<List<TransactionLocal>>

    @Query("SELECT * FROM TRANSACTIONLOCAL WHERE id = :id")
    fun getTransactionById(id: Int): Observable<TransactionLocal>

    @Query("SELECT * FROM TransactionLocal WHERE type = :type")
    fun getAllTransactionByType(type: String): Observable<List<TransactionLocal>>
}