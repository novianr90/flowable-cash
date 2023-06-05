package id.novian.flowablecash.data.remote.models.purchase


import com.google.gson.annotations.SerializedName

data class Purchases(
    @SerializedName("purchase")
    val purchase: List<Purchase>
)