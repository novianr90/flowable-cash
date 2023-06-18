package id.novian.flowablecash.data.remote.models.balancesheet


import com.google.gson.annotations.SerializedName

data class AccountBalance(
    @SerializedName("Credit")
    val credit: Int,
    @SerializedName("Debit")
    val debit: Int
)