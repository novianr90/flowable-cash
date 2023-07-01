package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheets
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface BalanceSheetService {

    @GET(Endpoints.BASE_BALANCE_SHEET)
    fun getBalanceSheets(): Observable<BalanceSheets>

    @Multipart
    @PUT(Endpoints.BASE_BALANCE_SHEET)
    fun updateBalanceSheet(
        @Part("account_name") accountName: RequestBody,
        @Part("account_balance") balance: RequestBody
    ): Observable<Unit>

    @GET(Endpoints.BASE_BALANCE_SHEET + "/")
    fun getBalanceSheetByAccountName(
        @Query("account_name") account_name: String
    ): Observable<BalanceSheet>
}