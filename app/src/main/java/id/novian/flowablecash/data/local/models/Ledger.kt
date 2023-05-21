package id.novian.flowablecash.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.novian.flowablecash.data.AccountType
import id.novian.flowablecash.data.Balance
import java.sql.Date

@Entity
data class Ledger(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val account: AccountType,
    val date: Date,
    val description: String,
    val balance: Balance,
    val generalReference: Int,
)
