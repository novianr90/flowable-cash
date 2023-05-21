package id.novian.flowablecash.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.novian.flowablecash.data.Balance
import java.sql.Date

@Entity
data class General(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val referenceNumber: Int,
    val date: Date,
    val description: String,
    val balance: Balance,
)
