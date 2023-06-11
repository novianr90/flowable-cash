package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.AccountName
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheets
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BalanceSheetService {
    @POST(Endpoints.BASE_BALANCE_SHEET)
    fun postNewBalanceSheet(
        @Query("account_name") accountName: AccountName,
        @Query("balance") balance: Int
    ): Observable<BalanceSheet>

    @GET(Endpoints.BASE_BALANCE_SHEET)
    fun getBalanceSheets(): Observable<BalanceSheets>

    @PUT(Endpoints.BASE_BALANCE_SHEET)
    fun updateBalanceSheet(
        @Query("balance_sheet_id") id: Int,
        @Query("account_name") accountName: AccountName,
        @Query("balance") balance: Int
    ): Observable<BalanceSheet>
}