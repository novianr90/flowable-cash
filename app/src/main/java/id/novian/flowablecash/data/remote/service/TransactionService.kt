package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.models.transaction.Transactions
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TransactionService {
    @GET("transactions")
    fun getTransactions(): Observable<Transactions>

    @GET("transactions/id")
    fun getTransactionById(
        @Query("id") id: Int,
    ): Observable<Transaction>

    @POST("transactions")
    fun postTransaction(
        @Query("transaction_name") name: String,
        @Query("transaction_date") date: String,
        @Query("transaction_type") type: String,
        @Query("transaction_total") total: Int,
        @Query("description") desc: String
    ): Observable<Transaction>

    @PUT("transactions")
    fun updateTransaction(
        @Query("id") id: Int,
        @Query("transaction_name") name: String,
        @Query("transaction_date") date: String,
        @Query("transaction_type") type: String,
        @Query("transaction_total") total: Int,
        @Query("description") desc: String
    ): Observable<Transaction>

    @DELETE("transactions")
    fun deleteTransaction(
        @Query("id") id: Int
    ): Observable<String>
}