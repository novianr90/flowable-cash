package id.novian.flowablecash.domain.repository

import id.novian.flowablecash.data.local.models.CashReceiptReport
import id.novian.flowablecash.data.local.repository.CashReceiptReportRepo
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.CashReceiptJournal
import io.reactivex.rxjava3.core.Observable

interface CashReceiptJournalRepository {
    fun getJournal(): Observable<List<CashReceiptJournal>>
}

class CashReceiptJournalRepositoryImpl(
    private val repo: MainRemoteRepository,
    private val local: CashReceiptReportRepo
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
                            credit = data.total,
                            accountAlreadyInserted = 0
                        )
                        new
                    }
                listOfCashReceipt
            }
            .onErrorResumeNext {
                local.getCashReceiptReport()
                    .map { listData ->
                        val newList = listData.map {
                            CashReceiptJournal(
                                id = it.id,
                                description = it.description,
                                date = it.date,
                                debit = it.cashReceiptDebit,
                                credit = it.cashReceiptCredit,
                                accountAlreadyInserted = it.accountAlreadyInserted
                            )
                        }
                        newList
                    }
            }
            .doAfterNext { listOfCashReceipt ->
                val filteredList = listOfCashReceipt.filter { it.accountAlreadyInserted == 0 }
                val newList = filteredList.map {
                    CashReceiptReport(
                        id = it.id,
                        date = it.date,
                        description = it.description,
                        cashReceiptDebit = it.debit,
                        cashReceiptCredit = it.credit,
                        accountAlreadyInserted = it.accountAlreadyInserted
                    )
                }

                newList.forEach { local.insertCashReceiptAccount(it) }
            }
    }
}