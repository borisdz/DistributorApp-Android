package mk.ukim.finki.my_distributor.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dashboard_data")
data class DashboardDataEntity(
    @PrimaryKey val customerId: Long,
    val jsonData: String,
    val timestamp: Long
)
