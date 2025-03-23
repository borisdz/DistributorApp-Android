package mk.ukim.finki.my_distributor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import mk.ukim.finki.my_distributor.domain.DashboardDataEntity
import mk.ukim.finki.my_distributor.domain.UserEntity

@Database(
    entities = [UserEntity::class, DashboardDataEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun dashboardDataDao(): DashboardDataDao
}