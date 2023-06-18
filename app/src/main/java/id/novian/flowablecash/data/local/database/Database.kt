package id.novian.flowablecash.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.novian.flowablecash.data.local.dao.BalanceSheetDao
import id.novian.flowablecash.data.local.dao.TransactionDao
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.models.TransactionLocal

@Database(entities = [TransactionLocal::class, BalanceSheetLocal::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): TransactionDao
    abstract fun balanceSheetDao(): BalanceSheetDao
}