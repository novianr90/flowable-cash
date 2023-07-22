package id.novian.flowablecash.data.remote.models.posting


import com.google.gson.annotations.SerializedName

data class Postings(
    @SerializedName("posting")
    val posting: Posting,
    @SerializedName("status")
    val status: String
)