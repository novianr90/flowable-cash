package id.novian.flowablecash.domain.repository

import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.CashReceiptJournal
import io.reactivex.rxjava3.core.Observable

interface CashReceiptJournalRepository {
    fun getJournal(): Observable<List<CashReceiptJournal>>
}

class CashReceiptJournalRepositoryImpl(
    private val repo: MainRemoteRepository
) : CashReceiptJournalRepository {
    override fun getJournal(): Observable<List<CashReceiptJournal>> {
        return repo.getAllSaleTypeTransactions()
            .map { listOfSale ->

                val listOfCashReceipt: List<CashReceiptJournal> = listOfSale.transaction
                    .sortedBy { it.date }
                    .map { data ->
                        val new = CashReceiptJournal(
                            id = data.id,
                            date = data.date,
                            description = data.description,
                            debit = data.total,
                            credit = data.total
                        )
                        new
                    }
                listOfCashReceipt
            }
    }
}