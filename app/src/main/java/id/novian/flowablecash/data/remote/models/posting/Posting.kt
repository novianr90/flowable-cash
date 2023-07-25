package id.novian.flowablecash.data.remote.models.posting


import com.google.gson.annotations.SerializedName

data class Posting(
    @SerializedName("account_id")
    val accountId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("posting_id")
    val postingId: Int,
    @SerializedName("transaction_id")
    val transactionId: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)