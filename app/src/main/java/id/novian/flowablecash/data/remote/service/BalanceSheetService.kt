package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheets
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface BalanceSheetService {

    @GET(Endpoints.BASE_BALANCE_SHEET)
    fun getBalanceSheets(): Observable<BalanceSheets>

    @PUT(Endpoints.BASE_BALANCE_SHEET)
    fun updateBalanceSheet(
        @Query("account_name") accountName: String,
        @Query("account_balance") balance: String
    ): Observable<BalanceSheet>

    @GET(Endpoints.BASE_BALANCE_SHEET)
    fun getBalanceSheetByAccountName(
        @Query("account_name") account_name: String
    ): Observable<BalanceSheet>
}