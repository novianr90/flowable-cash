package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.posting.Postings
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostingService {
    @POST(Endpoints.BASE_POSTING)
    fun recordNewPostingTracker(
        @Query("transaction_id") trxId: Int,
        @Query("account_id") accountId: Int
    ): Completable

    @GET(Endpoints.BASE_POSTING)
    fun getAllPostingTrack(): Maybe<Postings>

    @GET(Endpoints.BASE_POSTING + "/")
    fun getPostingByTrxAndAccId(
        @Query("transaction_id") trxId: Int,
        @Query("account_id") accountId: Int
    ): Maybe<Postings>
}