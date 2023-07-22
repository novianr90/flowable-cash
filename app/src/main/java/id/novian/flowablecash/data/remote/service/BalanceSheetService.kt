package id.novian.flowablecash.data.remote.service

import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheet
import id.novian.flowablecash.data.remote.models.balancesheet.BalanceSheets
import id.novian.flowablecash.data.remote.models.input.AccountInfo
import id.novian.flowablecash.data.remote.models.input.InputCreateAccounts
import id.novian.flowablecash.helpers.Endpoints
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BalanceSheetService {

    @GET(Endpoints.BASE_BALANCE_SHEET)
    fun getAllAccounts(
        @Query("account_month") month: Int
    ): Maybe<BalanceSheets>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.BASE_BALANCE_SHEET)
    fun recordNewAccounts(
        @Body input: InputCreateAccounts
    ): Completable

    @Headers("Content-Type: application/json")
    @PUT(Endpoints.BASE_BALANCE_SHEET)
    fun updateAccounts(
        @Body input: AccountInfo
    ): Completable

    @Headers("Content-Type: application/json")
    @PUT(Endpoints.BASE_BALANCE_SHEET + "/")
    fun updateSpecialAccounts(
        @Body input: AccountInfo
    ): Completable

    @GET(Endpoints.BASE_BALANCE_SHEET + "/")
    fun getAllAccountsByAccountName(
        @Query("account_name") account_name: String,
        @Query("account_month") month: Int
    ): Maybe<BalanceSheet>
}