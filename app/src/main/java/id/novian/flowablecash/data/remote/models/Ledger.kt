package id.novian.flowablecash.data.remote.models

import com.google.gson.annotations.SerializedName
import id.novian.flowablecash.data.AccountType
import id.novian.flowablecash.data.Balance
import java.sql.Date

data class Ledger(
    @SerializedName("account") val accountType: AccountType,
    @SerializedName("date") val date: Date,
    @SerializedName("description") val description: String,
    @SerializedName("balance") val balance: Balance,
    @SerializedName("general_ref") val generalReference: Int
)
