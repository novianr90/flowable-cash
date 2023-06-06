package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.models.transaction.Transactions
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PurchaseService {
    @GET(Endpoints.BASE_PURCHASES)
    fun getAllPurchaseTypeTransactions(): Observable<Transactions>

    @GET(Endpoints.BASE_PURCHASES)
    fun getPurchaseById(@Query("id") id: Int): Observable<Transaction>
}