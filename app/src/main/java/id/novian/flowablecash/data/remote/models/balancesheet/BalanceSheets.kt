package id.novian.flowablecash.data.remote.models.balancesheet

import com.google.gson.annotations.SerializedName
import id.novian.flowablecash.data.Balance

data class BalanceSheets(
    @SerializedName("balance-sheet") val balanceSheet: List<BalanceSheet>
)

data class BalanceSheet(
    @SerializedName("balance_sheet_id") val id: Int,
    @SerializedName("account_no") val accountNo: String,
    @SerializedName("account_name") val accountName: String,
    @SerializedName("account_balance") val balance: Balance,
    @SerializedName("created_at") val created: String,
    @SerializedName("updated_at") val updated: String
)
