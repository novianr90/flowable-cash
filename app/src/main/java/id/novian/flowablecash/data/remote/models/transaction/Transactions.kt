package id.novian.flowablecash.data.remote.models.transaction


import com.google.gson.annotations.SerializedName

data class Transactions(
    @SerializedName("transaction")
    val transaction: List<Transaction>
)