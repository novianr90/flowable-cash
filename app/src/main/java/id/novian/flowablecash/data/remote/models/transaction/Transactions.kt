package id.novian.flowablecash.data.remote.models.transaction

import com.google.gson.annotations.SerializedName
import id.novian.flowablecash.data.remote.models.NTransactions

data class Transactions(
    @SerializedName("pengeluaran")
    val pengeluaran: List<NTransactions>?,
    @SerializedName("pemasukkan")
    val pemasukkan: List<NTransactions>?,
    @SerializedName("status")
    val status: String
)