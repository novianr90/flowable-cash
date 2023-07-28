package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.Pemasukkan
import id.novian.flowablecash.data.remote.models.PemasukkanList
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PemasukkanService {
    @GET(Endpoints.BASE_PEMASUKKAN)
    fun getAllPemasukkan(): Observable<PemasukkanList>

    @GET(Endpoints.BASE_PEMASUKKAN + "/")
    fun getPemasukkanById(@Query("id") id: Int): Observable<Pemasukkan>

    @POST(Endpoints.BASE_PEMASUKKAN)
    fun postNewPemasukkanTransactions(
        @Query("transaction_name") name: String,
        @Query("transaction_date") date: String,
        @Query("transaction_total") total: Int,
        @Query("transaction_payment") payment: String,
        @Query("description") desc: String?
    ): Completable
}