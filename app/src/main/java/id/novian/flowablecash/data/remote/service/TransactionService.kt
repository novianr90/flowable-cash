package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.transaction.Transaction
import id.novian.flowablecash.data.remote.models.transaction.Transactions
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TransactionService {
    @GET(Endpoints.BASE_TRANSACTIONS)
    fun getTransactions(): Maybe<Transactions>

    @GET(Endpoints.BASE_TRANSACTIONS + "/")
    fun getTransactionById(
        @Query("id") id: Int,
    ): Observable<Transaction>

    @POST(Endpoints.BASE_TRANSACTIONS)
    fun postTransaction(
        @Query("transaction_name") name: String,
        @Query("transaction_date") date: String,
        @Query("transaction_type") type: String,
        @Query("transaction_total") total: Int,
        @Query("fee_type") feeType: String,
        @Query("transaction_fee") fee: Int,
        @Query("description") desc: String,
        @Query("transaction_payment") payment: String
    ): Observable<Unit>

    @PUT(Endpoints.BASE_TRANSACTIONS)
    fun updateTransaction(
        @Query("id") id: Int,
        @Query("transaction_name") name: String,
        @Query("transaction_date") date: String,
        @Query("transaction_type") type: String,
        @Query("transaction_total") total: Int,
        @Query("fee_type") feeType: String,
        @Query("transaction_fee") fee: Int,
        @Query("description") desc: String,
        @Query("already_posted") alreadyPosted: Int
    ): Completable

    @DELETE(Endpoints.BASE_TRANSACTIONS)
    fun deleteTransaction(
        @Query("id") id: Int
    ): Observable<Response<Unit>>

    @GET(Endpoints.BASE_TRANSACTIONS + "/type")
    fun getAllTransactionByType(
        @Query("type") type: String
    ): Maybe<Transactions>
}