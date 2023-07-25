package id.novian.flowablecash.data.remote.models.balancesheet


import com.google.gson.annotations.SerializedName

data class BalanceSheets(
    @SerializedName("balance_sheet")
    val balanceSheet: List<BalanceSheet>,
    @SerializedName("status")
    val status: String
)