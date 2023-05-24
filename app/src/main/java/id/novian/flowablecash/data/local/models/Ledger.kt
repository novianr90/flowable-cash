package id.novian.flowablecash.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.novian.flowablecash.data.AccountType
import id.novian.flowablecash.data.Balance

@Entity
data class Ledger(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val account: AccountType,
    val date: String,
    val description: String,
    val balance: Balance,
    val generalReference: Int,
)
