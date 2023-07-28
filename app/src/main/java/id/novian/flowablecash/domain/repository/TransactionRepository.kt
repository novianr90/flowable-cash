package id.novian.flowablecash.domain.repository

import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.TransactionDomain
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface TransactionRepository {
    fun getAllTransactions(): Observable<Pair<List<TransactionDomain>, List<TransactionDomain>>>

    fun updateTransaction(
        id: Int,
        date: String,
        total: Int,
        type: String,
        description: String
    ): Completable

    fun deleteTransaction(id: Int, type: String): Completable

    fun createTransactions(
        name: String,
        date: String,
        total: Int,
        payment: String,
        description: String?,
        type: String
    ): Completable
}

class TransactionRepositoryImpl(
    private val remote: MainRemoteRepository,
) : TransactionRepository {
    override fun getAllTransactions(): Observable<Pair<List<TransactionDomain>, List<TransactionDomain>>> {
        return remote.getAllTransactions()
            .map { list ->
                val pemasukkan = list.pemasukkan.map {
                    val new = TransactionDomain(
                        id = it.id,
                        transactionName = it.name,
                        transactionDate = it.date,
                        transactionType = "Pemasukkan",
                        transactionDescription = it.description,
                        total = it.total,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt,
                        payment = it.transactionPayment
                    )
                    new
                }

                val pengeluaran = list.pengeluaran.map {
                    val new = TransactionDomain(
                        id = it.id,
                        transactionName = it.name,
                        transactionDate = it.date,
                        transactionType = "Pengeluaran",
                        transactionDescription = it.description,
                        total = it.total,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt,
                        payment = it.transactionPayment
                    )
                    new
                }

                Pair(pemasukkan, pengeluaran)
            }
    }

    override fun updateTransaction(
        id: Int,
        date: String,
        total: Int,
        type: String,
        description: String
    ): Completable {
        return remote.updateTransaction(
            id = id, date = date, total = total, type = type, description = description
        )
    }

    override fun deleteTransaction(id: Int, type: String): Completable {
        return remote.deleteTransaction(id = id, type = type)
    }

    override fun createTransactions(
        name: String,
        date: String,
        total: Int,
        payment: String,
        description: String?,
        type: String
    ): Completable {
        return when (type) {
            "Pemasukkan" -> remote.createPemasukkan(
                name = name,
                date = date,
                total = total,
                payment = payment,
                description = description
            )

            "Pengeluaran" -> remote.createPengeluaran(
                name = name,
                date = date,
                total = total,
                payment = payment,
                description = description
            )

            else -> Completable.error(Throwable("Transaction Type is Unknown"))
        }
    }

}