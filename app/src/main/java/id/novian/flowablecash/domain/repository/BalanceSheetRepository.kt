package id.novian.flowablecash.domain.repository

import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.data.local.models.BalanceSheetLocal
import id.novian.flowablecash.data.local.repository.BalanceSheetLocalRepository
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.repository.MainRemoteRepository
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.helpers.Mapper
import io.reactivex.rxjava3.core.Observable

interface BalanceSheetRepository {
    fun getBalanceSheet(): Observable<List<BalanceSheetDomain>>

    fun createBalanceSheet(
        accountName: String,
        balance: Int
    ): Observable<BalanceSheetDomain>

    fun updateBalanceSheet(
        id: Int,
        balance: Int,
        accountName: String
    ): Observable<BalanceSheetDomain>
}

class BalanceSheetRepositoryImpl(
    private val local: BalanceSheetLocalRepository,
    private val remote: MainRemoteRepository,
    private val remoteMapper: Mapper<BalanceSheet, BalanceSheetDomain>,
    private val localMapper: Mapper<BalanceSheetLocal, BalanceSheetDomain>
): BalanceSheetRepository {
    override fun getBalanceSheet(): Observable<List<BalanceSheetDomain>> {
        return remote.getBalanceSheet()
            .map { data -> data.balanceSheet.map { value -> remoteMapper.mapToDomain(value) } }
            .doOnNext { data ->
                data.map { local.insertBalanceSheetToLocal(localMapper.mapToModel(it)) }
            }
            .onErrorResumeNext { _ ->
                local.getBalanceSheet()
                    .map { data -> data.map { localMapper.mapToDomain(it) } }
            }
    }

    override fun createBalanceSheet(
        accountName: String,
        balance: Int
    ): Observable<BalanceSheetDomain> {
        return remote.createBalanceSheet(
            accountName = accountName,
            balance = balance
        )
            .map { data -> remoteMapper.mapToDomain(data) }
    }

    override fun updateBalanceSheet(
        id: Int,
        balance: Int,
        accountName: String
    ): Observable<BalanceSheetDomain> {
        return remote.updateBalanceSheet(
            id = id,
            accountName = accountName,
            balance = balance
        )
            .map { data -> remoteMapper.mapToDomain(data)}
    }

}