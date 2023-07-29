package id.novian.flowablecash.domain.repository

import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.PurchasesJournal
import io.reactivex.rxjava3.core.Observable

interface PurchasesJournalRepository {
    fun getJournal(): Observable<List<PurchasesJournal>>
}

class PurchasesJournalRepositoryImpl(
    private val repo: MainRemoteRepository,
) : PurchasesJournalRepository {
    override fun getJournal(): Observable<List<PurchasesJournal>> {
        return repo.getAllPengeluaran()
            .map { listOfTransactions ->
                val listOfPurchases: List<PurchasesJournal> = listOfTransactions.pengeluaran
                    .filter { it.name != "Membayar Hutang" }
                    .map { data ->
                        val new = PurchasesJournal(
                            id = data.id,
                            date = data.date,
                            description = data.description,
                            debit = data.total,
                            credit = data.total,
                            name = data.name
                        )
                        new
                    }
                listOfPurchases
            }
    }

}