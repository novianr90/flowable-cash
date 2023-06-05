package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.purchase.Purchase
import id.novian.flowablecash.data.remote.models.purchase.Purchases
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PurchaseService {
    @GET("purchases")
    fun getAllPurchaseTypeTransactions(): Observable<Purchases>

    @GET("purchases")
    fun getPurchaseById(@Query("id") id: Int): Observable<Purchase>
}