package id.novian.flowablecash.data.local.repository

import id.novian.flowablecash.data.local.dao.CashReceiptReportDao
import id.novian.flowablecash.data.local.models.CashReceiptReport
import io.reactivex.rxjava3.core.Observable

interface CashReceiptReportRepo {
    fun insertCashReceiptAccount(query: CashReceiptReport)
    fun deleteCashReceiptReport()
    fun deleteSpecificCashReceiptAccount(id: Int)
    fun getCashReceiptReport(): Observable<List<CashReceiptReport>>
}

class CashReceiptReportRepoImpl(
    private val dao: CashReceiptReportDao
) : CashReceiptReportRepo {
    override fun insertCashReceiptAccount(query: CashReceiptReport) {
        return dao.insertCashReceiptReport(query)
    }

    override fun deleteCashReceiptReport() {
        return dao.deleteCashReceiptReport()
    }

    override fun deleteSpecificCashReceiptAccount(id: Int) {
        return dao.deleteSpecificCashReceiptReport(id)
    }

    override fun getCashReceiptReport(): Observable<List<CashReceiptReport>> {
        return dao.getCashReceiptReport()
    }
}