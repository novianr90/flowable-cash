package id.novian.flowablecash.data.remote.models.sale


import com.google.gson.annotations.SerializedName

data class Sale(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("total")
    val total: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)