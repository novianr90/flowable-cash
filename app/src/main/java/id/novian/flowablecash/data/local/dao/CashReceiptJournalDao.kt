package id.novian.flowablecash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.flowablecash.data.local.models.CashReceiptJournalLocal
import io.reactivex.rxjava3.core.Observable

@Dao
interface CashReceiptJournalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewAccountForCashReceiptJournal(query: CashReceiptJournalLocal)

    @Query("SELECT * FROM CashReceiptJournalLocal")
    fun getCashReceiptJournal(): Observable<List<CashReceiptJournalLocal>>

    @Query(
        """
        UPDATE CashReceiptJournalLocal
        SET cash_receipt_balance = :newBalance
        WHERE cash_receipt_journal_id = :id
    """
    )
    fun update(
        newBalance: String,
        id: Int?
    )

    @Query(
        """
        UPDATE CashReceiptJournalLocal
        SET cash_receipt_balance = :newBalance
        WHERE cash_receipt_date = :date
    """
    )
    fun update(
        newBalance: String,
        date: String?
    )

    @Query("DELETE FROM CashReceiptJournalLocal WHERE cash_receipt_journal_id = :id")
    fun deleteOneCashReceiptJournal(id: Int)

    @Query("DELETE FROM CashReceiptJournalLocal")
    fun deleteCurrentReceiptJournal()
}