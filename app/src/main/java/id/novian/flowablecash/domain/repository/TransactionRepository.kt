package id.novian.flowablecash.domain.repository

import id.novian.flowablecash.data.local.models.TransactionLocal
import id.novian.flowablecash.data.local.repository.TransactionLocalRepository
import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Mapper
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

interface TransactionRepository {
    fun getAllTransactions(): Observable<List<TransactionDomain>>
    fun getAllSaleTransactions(): Observable<List<TransactionDomain>>
    fun getAllPurchaseTransactions(): Observable<List<TransactionDomain>>

    fun getTransaction(id: Int): Observable<TransactionDomain>

    fun createTransaction(
        name: String,
        date: String,
        total: Int,
        type: String,
        feeType: String,
        fee: Int,
        description: String
    ): Observable<Unit>

    fun updateTransaction(
        id: Int,
        name: String,
        date: String,
        total: Int,
        fee: Int,
        feeType: String,
        type: String,
        description: String
    ): Observable<TransactionDomain>

    fun deleteTransaction(id: Int): Observable<Response<Unit>>
}

class TransactionRepositoryImpl(
    private val remote: MainRemoteRepository,
    private val local: TransactionLocalRepository,
    private val remoteMapper: Mapper<Transaction, TransactionDomain>,
    private val localMapper: Mapper<TransactionLocal, TransactionDomain>
) : TransactionRepository {
    override fun getAllTransactions(): Observable<List<TransactionDomain>> {
        return remote.getTransactions()
            .map { data -> data.transaction.map { remoteMapper.mapToDomain(it) } }
            .doOnNext { data ->
                data.map { local.insertTransaction(localMapper.mapToModel(it)) }
            }
            .onErrorResumeNext {
                local.getAllTransaction()
                    .map { local -> local.map { localMapper.mapToDomain(it) } }
            }
    }

    override fun getAllSaleTransactions(): Observable<List<TransactionDomain>> {
        return remote.getAllSaleTypeTransactions()
            .map { data -> data.transaction.map { remoteMapper.mapToDomain(it) } }
            .doOnNext { data ->
                data.map { local.insertTransaction(localMapper.mapToModel(it)) }
            }
            .onErrorResumeNext {
                local.getAllTypeTransaction("Sale")
                    .map { local -> local.map { localMapper.mapToDomain(it) } }
            }
    }

    override fun getAllPurchaseTransactions(): Observable<List<TransactionDomain>> {
        return remote.getAllPurchaseTypeTransactions()
            .map { data -> data.transaction.map { remoteMapper.mapToDomain(it) } }
            .doOnNext { data ->
                data.map { local.insertTransaction(localMapper.mapToModel(it)) }
            }
            .onErrorResumeNext {
                local.getAllTypeTransaction("Purchase")
                    .map { local -> local.map { localMapper.mapToDomain(it) } }
            }
    }

    override fun getTransaction(id: Int): Observable<TransactionDomain> {
        return remote.getTransactionById(id)
            .map { data -> remoteMapper.mapToDomain(data) }
            .onErrorResumeNext {
                local.getTransactionById(id)
                    .map { local -> localMapper.mapToDomain(local) }
            }
    }

    override fun createTransaction(
        name: String,
        date: String,
        total: Int,
        type: String,
        feeType: String,
        fee: Int,
        description: String
    ): Observable<Unit> {
        return remote.postTransaction(
            name = name,
            date = date,
            total = total,
            type = type,
            description = description,
            feeType = feeType,
            fee = fee,
        )
    }

    override fun updateTransaction(
        id: Int,
        name: String,
        date: String,
        total: Int,
        fee: Int,
        feeType: String,
        type: String,
        description: String
    ): Observable<TransactionDomain> {
        return remote.updateTransaction(
            name = name,
            date = date,
            total = total,
            type = type,
            description = description,
            id = id,
            feeType = feeType,
            fee = fee,
        )
            .map { data -> remoteMapper.mapToDomain(data) }
    }

    override fun deleteTransaction(id: Int): Observable<Response<Unit>> {
        return remote.deleteTransaction(id)
            .doOnNext {
                local.deleteTransaction(id)
            }
    }
}