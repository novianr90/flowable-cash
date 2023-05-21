package id.novian.flowablecash.data.remote.models

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class Daily(
    @SerializedName("date") val date: Date,
    @SerializedName("name") val name: String,
    @SerializedName("type") val transactionType: String,
    @SerializedName("total") val total: Int,
    @SerializedName("description") val description: String
)
