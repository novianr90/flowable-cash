package id.novian.flowablecash.data.remote.repository

import id.novian.flowablecash.data.remote.models.sale.Sale
import id.novian.flowablecash.data.remote.models.sale.Sales
import id.novian.flowablecash.data.remote.service.SaleService
import io.reactivex.rxjava3.core.Observable

interface SaleRemoteRepository {
    fun getAllSaleTypeTransactions(): Observable<Sales>
    fun getSaleTypeTransaction(id: Int): Observable<Sale>
}

class SaleRemoteRepositoryImpl(
    private val api: SaleService
) : SaleRemoteRepository {
    override fun getAllSaleTypeTransactions(): Observable<Sales> {
        return api.getAllSaleTypeTransactions()
    }

    override fun getSaleTypeTransaction(id: Int): Observable<Sale> {
        return api.getSaleTypeTransaction(id)
    }
}