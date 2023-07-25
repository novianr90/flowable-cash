package id.novian.flowablecash.data.local.repository

import id.novian.flowablecash.data.local.dao.PurchasesReportDao
import id.novian.flowablecash.data.local.models.PurchasesReport
import io.reactivex.rxjava3.core.Observable

interface PurchasesReportRepo {
    fun insertPurchasesAccounts(query: PurchasesReport)

    fun deletePurchasesReport()

    fun deleteSpecificPurchasesAccount(id: Int)

    fun getPurchasesReport(): Observable<List<PurchasesReport>>
}

class PurchasesReportRepoImpl(
    private val dao: PurchasesReportDao
) : PurchasesReportRepo {
    override fun insertPurchasesAccounts(query: PurchasesReport) {
        return dao.insertNewAccountToPurchases(query)
    }

    override fun deletePurchasesReport() {
        return dao.deletePurchasesReport()
    }

    override fun deleteSpecificPurchasesAccount(id: Int) {
        return deleteSpecificPurchasesAccount(id)
    }

    override fun getPurchasesReport(): Observable<List<PurchasesReport>> {
        return dao.getPurchasesReport()
    }
}