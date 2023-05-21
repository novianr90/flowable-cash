package id.novian.flowablecash.data.remote.models

import com.google.gson.annotations.SerializedName
import id.novian.flowablecash.data.Balance
import java.sql.Date

data class General(
    @SerializedName("date") val date: Date,
    @SerializedName("description") val description: String,
    @SerializedName("balance") val balance: Balance,
    @SerializedName("daily_ref") val dailyReference: Int
)
