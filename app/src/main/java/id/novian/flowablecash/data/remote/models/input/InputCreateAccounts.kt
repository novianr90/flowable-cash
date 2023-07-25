package id.novian.flowablecash.data.remote.models.input

import com.google.gson.annotations.SerializedName
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance

data class InputCreateAccounts(
    @SerializedName("account_name")
    val accountName: String,
    @SerializedName("account_balance")
    val balance: AccountBalance,
    @SerializedName("account_month")
    val month: Int
)
