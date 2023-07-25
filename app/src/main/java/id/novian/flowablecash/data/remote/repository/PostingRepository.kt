package id.novian.flowablecash.data.remote.repository

import id.novian.flowablecash.data.remote.models.posting.Postings
import id.novian.flowablecash.data.remote.service.PostingService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface PostingRepository {
    fun recordNewPosting(trxId: Int, accId: Int): Completable
    fun getAllTracker(): Maybe<Postings>
    fun getPostingByTrxIdAndAccId(trxId: Int, accId: Int): Maybe<Postings>
}

class PostingRepositoryImpl(
    private val api: PostingService
): PostingRepository {
    override fun recordNewPosting(trxId: Int, accId: Int): Completable {
        return api.recordNewPostingTracker(trxId, accId)
    }

    override fun getAllTracker(): Maybe<Postings> {
        return api.getAllPostingTrack()
    }

    override fun getPostingByTrxIdAndAccId(trxId: Int, accId: Int): Maybe<Postings> {
        return api.getPostingByTrxAndAccId(trxId, accId)
    }
}