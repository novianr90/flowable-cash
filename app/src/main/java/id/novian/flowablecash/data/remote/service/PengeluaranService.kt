package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.Pengeluaran
import id.novian.flowablecash.data.remote.models.PengeluaranList
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PengeluaranService {
    @GET(Endpoints.BASE_PENGELUARAN)
    fun getAllPengeluaran(): Observable<PengeluaranList>

    @GET(Endpoints.BASE_PENGELUARAN + "/")
    fun getPengeluaranById(@Query("id") id: Int): Observable<Pengeluaran>

    @POST(Endpoints.BASE_PENGELUARAN)
    fun postNewPemasukkanTransactions(
        @Query("transaction_name") name: String,
        @Query("transaction_date") date: String,
        @Query("transaction_total") total: Int,
        @Query("transaction_payment") payment: String,
        @Query("description") desc: String?
    ): Completable
}