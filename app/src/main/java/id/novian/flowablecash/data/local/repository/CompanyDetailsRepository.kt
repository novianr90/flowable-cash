package id.novian.flowablecash.data.local.repository

import id.novian.flowablecash.data.local.dao.CompanyDetailsDao
import id.novian.flowablecash.data.local.models.CompanyDetails
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CompanyDetailsRepository {
    fun insertCompanyDetails(query: CompanyDetails): Completable
    fun getCompanyDetails(id: Int): Single<CompanyDetails>
    fun deleteCompanyDetails(): Completable
}

class CompanyDetailsRepositoryImpl(
    private val dao: CompanyDetailsDao
): CompanyDetailsRepository {
    override fun insertCompanyDetails(query: CompanyDetails): Completable {
        return Completable.fromAction { dao.insertCompanyDetails(query) }
    }

    override fun getCompanyDetails(id: Int): Single<CompanyDetails> {
        return dao.getCompanyDetails(id)
    }

    override fun deleteCompanyDetails(): Completable {
        return Completable.fromAction { dao.deleteCompanyDetails() }
    }
}