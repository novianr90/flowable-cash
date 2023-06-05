package id.novian.flowablecash.data.remote.repository

import id.novian.flowablecash.data.remote.models.purchase.Purchase
import id.novian.flowablecash.data.remote.models.purchase.Purchases
import id.novian.flowablecash.data.remote.service.PurchaseService
import io.reactivex.rxjava3.core.Observable

interface PurchaseRemoteRepository {
    fun getAllPurchaseTypeTransactions(): Observable<Purchases>
    fun getPurchaseTypeTransaction(id: Int): Observable<Purchase>
}

class PurchaseRemoteRepositoryImpl(
    private val api: PurchaseService
) : PurchaseRemoteRepository {
    override fun getAllPurchaseTypeTransactions(): Observable<Purchases> {
        return api.getAllPurchaseTypeTransactions()
    }

    override fun getPurchaseTypeTransaction(id: Int): Observable<Purchase> {
        return api.getPurchaseById(id)
    }
}