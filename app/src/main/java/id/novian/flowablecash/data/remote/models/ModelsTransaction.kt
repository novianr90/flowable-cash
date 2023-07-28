package id.novian.flowablecash.data.remote.models

import com.google.gson.annotations.SerializedName

data class Pemasukkan(
    @SerializedName("pemasukkan")
    val pemasukkan: NTransactions,
    @SerializedName("status")
    val status: String
)

data class PemasukkanList(
    @SerializedName("pemasukkan")
    val pemasukkan: List<NTransactions>,
    @SerializedName("status")
    val status: String
)

data class Pengeluaran(
    @SerializedName("pengeluaran")
    val pengeluaran: NTransactions,
    @SerializedName("status")
    val status: String
)

data class PengeluaranList(
    @SerializedName("pengeluaran")
    val pengeluaran: List<NTransactions>,
    @SerializedName("status")
    val status: String
)
