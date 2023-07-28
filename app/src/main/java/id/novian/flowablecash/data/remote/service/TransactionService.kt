package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.transaction.Transactions
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface TransactionService {
    @GET(Endpoints.BASE_TRANSACTIONS)
    fun getAllTransactions(): Observable<Transactions>

    @DELETE(Endpoints.BASE_TRANSACTIONS)
    fun deleteTransaction(
        @Query("id") id: Int,
        @Query("transaction_type") type: String
    ): Completable

    @PUT(Endpoints.BASE_TRANSACTIONS)
    fun updateTransactions(
        @Query("transaction_type") type: String,
        @Query("id") id: Int,
        @Query("transaction_total") total: Int,
        @Query("description") desc: String?,
        @Query("transaction_date") date: String
    ): Completable
}