package id.novian.flowablecash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.flowablecash.data.local.models.CompanyDetails
import io.reactivex.rxjava3.core.Single

@Dao
interface CompanyDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompanyDetails(query: CompanyDetails)

    @Query("SELECT * FROM CompanyDetails WHERE company_id = :id")
    fun getCompanyDetails(id: Int): Single<CompanyDetails>

    @Query("DELETE FROM CompanyDetails")
    fun deleteCompanyDetails()
}