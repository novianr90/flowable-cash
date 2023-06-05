package id.novian.flowablecash.data.remote.models.sale


import com.google.gson.annotations.SerializedName

data class Sales(
    @SerializedName("sale")
    val sale: List<Sale>
)