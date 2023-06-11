package id.novian.flowablecash.data.remote.models.transaction


import com.google.gson.annotations.SerializedName

data class Transaction(
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
    @SerializedName("type")
    val type: String,
    @SerializedName("transaction_fee")
    val fee: Int,
    @SerializedName("fee_type")
    val feeType: String,
    @SerializedName("updated_at")
    val updatedAt: String
)