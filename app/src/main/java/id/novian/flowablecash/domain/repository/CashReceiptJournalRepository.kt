package id.novian.flowablecash.domain.repository

import com.google.gson.Gson
import id.novian.flowablecash.data.local.models.CashReceiptJournalLocal
import id.novian.flowablecash.data.local.repository.CashReceiptJournalLocalRepository
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.helpers.Mapper
import io.reactivex.rxjava3.core.Observable

interface CashReceiptJournalRepository {
    fun insertNewData(): Observable<Unit>
    fun getJournal(): Observable<List<CashReceiptJournal>>
    fun updateJournal(id: Int, newBalance: AccountBalance): Observable<Unit>
    fun updateJournal(date: String, newBalance: AccountBalance): Observable<Unit>
    fun deleteJournal(id: Int): Observable<Unit>
    fun deleteJournal(): Observable<Unit>
}

class CashReceiptJournalRepositoryImpl(
    private val localRepo: CashReceiptJournalLocalRepository,
    private val sale: TransactionRepository,
    private val mapper: Mapper<CashReceiptJournalLocal, CashReceiptJournal>,
    private val gson: Gson
) : CashReceiptJournalRepository {
    override fun insertNewData(): Observable<Unit> {
        return sale.getAllSaleTransactions()
            .flatMap { data ->
                val query = arrayListOf<CashReceiptJournal>()
                data.map {
                    val aQuery = CashReceiptJournal(
                        id = null,
                        date = it.transactionDate,
                        description = it.transactionDescription,
                        balance = AccountBalance(it.total, it.total)
                    )
                    query.add(aQuery)
                }
                Observable.just(query.forEach { localRepo.insertNewData(mapper.mapToModel(it)) })
            }
    }

    override fun getJournal(): Observable<List<CashReceiptJournal>> {
        return localRepo.getJournal()
            .map { it.map { data -> mapper.mapToDomain(data) } }
    }

    override fun updateJournal(id: Int, newBalance: AccountBalance): Observable<Unit> {
        val balanceJson = gson.toJson(newBalance)
        return Observable.just(
            localRepo.updateBalanceByIdOrDate(
                newBalance = balanceJson,
                id = id,
                date = null
            )
        )
    }

    override fun updateJournal(date: String, newBalance: AccountBalance): Observable<Unit> {
        val balanceJson = gson.toJson(newBalance)
        return Observable.just(
            localRepo.updateBalanceByIdOrDate(
                newBalance = balanceJson,
                id = null,
                date = date
            )
        )
    }

    override fun deleteJournal(id: Int): Observable<Unit> {
        return Observable.just(localRepo.deleteOneAccount(id))
    }

    override fun deleteJournal(): Observable<Unit> {
        return Observable.just(localRepo.deleteJournal())
    }
}