package id.novian.flowablecash.domain.repository

import id.novian.flowablecash.data.local.models.PurchasesReport
import id.novian.flowablecash.data.local.repository.PurchasesReportRepo
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.PurchasesJournal
import io.reactivex.rxjava3.core.Observable

interface PurchasesJournalRepository {
    fun getJournal(): Observable<List<PurchasesJournal>>
}

class PurchasesJournalRepositoryImpl(
    private val repo: MainRemoteRepository,
    private val local: PurchasesReportRepo
) : PurchasesJournalRepository {
    override fun getJournal(): Observable<List<PurchasesJournal>> {
        return repo.getAllPurchaseTypeTransactions()
            .map { listOfTransactions ->
                val listOfPurchases: List<PurchasesJournal> = listOfTransactions.transaction
                    .map { data ->
                        val new = PurchasesJournal(
                            id = data.id,
                            date = data.date,
                            description = data.description,
                            debit = data.total,
                            credit = data.total
                        )
                        new
                    }
                listOfPurchases
            }
            .onErrorResumeNext {
                local.getPurchasesReport()
                    .map { listData ->
                        val newList = listData.map {
                            PurchasesJournal(
                                id = it.id,
                                date = it.date,
                                description = it.description,
                                debit = it.purchasesDebit,
                                credit = it.purchasesCredit,
                            )
                        }
                        newList
                    }
            }
            .doAfterNext { listOfPurchases ->
                val newList = listOfPurchases.map {
                    PurchasesReport(
                        id = it.id,
                        date = it.date,
                        description = it.description,
                        purchasesDebit = it.debit,
                        purchasesCredit = it.credit,
                    )
                }

                newList.forEach { local.insertPurchasesAccounts(it) }
            }
    }

}