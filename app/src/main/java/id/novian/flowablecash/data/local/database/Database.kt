package id.novian.flowablecash.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.novian.flowablecash.data.local.dao.BalanceSheetDao
import id.novian.flowablecash.data.local.dao.CashReceiptReportDao
import id.novian.flowablecash.data.local.dao.PurchasesReportDao
import id.novian.flowablecash.data.local.dao.TransactionDao
import id.novian.flowablecash.data.local.dao.UpdateBalanceSheetDao
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.models.CashReceiptReport
import id.novian.flowablecash.data.local.models.PurchasesReport
import id.novian.flowablecash.data.local.models.TransactionLocal
import id.novian.flowablecash.data.local.models.UpdateModelBalanceSheet

@Database(
    entities =
    [
        TransactionLocal::class,
        BalanceSheetLocal::class,
        PurchasesReport::class,
        CashReceiptReport::class,
        UpdateModelBalanceSheet::class
    ], version = 8
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): TransactionDao
    abstract fun balanceSheetDao(): BalanceSheetDao
    abstract fun purchasesDao(): PurchasesReportDao
    abstract fun cashReceiptDao(): CashReceiptReportDao
    abstract fun updateBalanceSheetDao(): UpdateBalanceSheetDao
}