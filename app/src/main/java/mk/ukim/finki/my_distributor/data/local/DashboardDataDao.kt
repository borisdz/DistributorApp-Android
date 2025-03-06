package mk.ukim.finki.my_distributor.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mk.ukim.finki.my_distributor.domain.DashboardDataEntity

@Dao
interface DashboardDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDashboardData(data: DashboardDataEntity)

    @Query("select * from dashboard_data where customerId = :customerId limit 1")
    suspend fun getDashboardData(customerId: Long): DashboardDataEntity?
}