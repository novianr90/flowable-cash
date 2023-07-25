package id.novian.flowablecash.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyDetails(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "company_id") val id: Int,
    @ColumnInfo(name = "company_name") val companyName: String,
    @ColumnInfo(name = "company_address") val companyAddress: String,
    @ColumnInfo(name = "company_desc") val companyDesc: String
)
