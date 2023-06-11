package id.novian.flowablecash.data

import com.google.gson.annotations.SerializedName

data class Balance(
    @SerializedName("debit") val debit: Long,
    @SerializedName("credit") val credit: Long
)
