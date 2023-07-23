package id.novian.flowablecash.data.remote.models.balancesheet


import com.google.gson.annotations.SerializedName

data class AccountBalanceSheet(
    @SerializedName("balance_sheet")
    val accounts: BalanceSheet,
    @SerializedName("status")
    val status: String
)

data class BalanceSheet(
    @SerializedName("account_balance")
    val accountBalance: AccountBalance,
    @SerializedName("account_name")
    val accountName: String,
    @SerializedName("account_no")
    val accountNo: String,
    @SerializedName("balance_sheet_id")
    val balanceSheetId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("account_month")
    val month: Int
)