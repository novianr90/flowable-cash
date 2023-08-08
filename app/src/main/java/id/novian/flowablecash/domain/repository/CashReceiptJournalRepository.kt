package id.novian.flowablecash.domain.repository

import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.helpers.CalendarHelper
import io.reactivex.rxjava3.core.Observable

interface CashReceiptJournalRepository {
    fun getJournal(): Observable<List<CashReceiptJournal>>
}

class CashReceiptJournalRepositoryImpl(
    private val repo: MainRemoteRepository,
    private val calendarHelper: CalendarHelper
) : CashReceiptJournalRepository {
    override fun getJournal(): Observable<List<CashReceiptJournal>> {
        return repo.getAllPemasukkan()
            .map { listOfSale ->
                val listOfCashReceipt: List<CashReceiptJournal> = listOfSale.pemasukkan
                    .filter {
                        val parts = it.date.split("-")
                        val months = if (parts.size == 3) {
                            parts[1].toInt()
                        } else -1

                        months == calendarHelper.getMonth()
                    }
                    .sortedBy { it.date }
                    .map { data ->
                        val new = CashReceiptJournal(
                            id = data.id,
                            date = data.date,
                            description = data.description,
                            debit = data.total,
                            credit = data.total,
                            name = data.name
                        )
                        new
                    }
                listOfCashReceipt
            }
    }
}