package id.novian.flowablecash.data.remote.models.input


import com.google.gson.annotations.SerializedName
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance

data class AccountInfo(
    @SerializedName("account_balance")
    val accountBalance: AccountBalance,
    @SerializedName("account_month")
    val accountMonth: Int,
    @SerializedName("account_name")
    val accountName: String?,
    @SerializedName("balance_sheet_id")
    val balanceSheetId: Int?
)