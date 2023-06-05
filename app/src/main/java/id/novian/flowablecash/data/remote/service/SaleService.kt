package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.sale.Sale
import id.novian.flowablecash.data.remote.models.sale.Sales
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SaleService {
    @GET("sales")
    fun getAllSaleTypeTransactions(): Observable<Sales>

    @GET("sales")
    fun getSaleTypeTransaction(@Query("id") id: Int): Observable<Sale>
}