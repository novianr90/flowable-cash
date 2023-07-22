package id.novian.flowablecash.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.novian.flowablecash.data.local.dao.AccountDao
import id.novian.flowablecash.data.local.dao.CashReceiptReportDao
import id.novian.flowablecash.data.local.dao.CompanyDetailsDao
import id.novian.flowablecash.data.local.dao.PurchasesReportDao
import id.novian.flowablecash.data.local.dao.TransactionDao
import id.novian.flowablecash.data.local.models.Accounts
import id.novian.flowablecash.data.local.models.CashReceiptReport
import id.novian.flowablecash.data.local.models.CompanyDetails
import id.novian.flowablecash.data.local.models.PurchasesReport
import id.novian.flowablecash.data.local.models.TransactionLocal

@Database(
    entities =
    [
        TransactionLocal::class,
        Accounts::class,
        PurchasesReport::class,
        CashReceiptReport::class,
        CompanyDetails::class,
    ], version = 12
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): TransactionDao
    abstract fun accountsDao(): AccountDao
    abstract fun purchasesDao(): PurchasesReportDao
    abstract fun cashReceiptDao(): CashReceiptReportDao
    abstract fun companyDetailsDao(): CompanyDetailsDao
}