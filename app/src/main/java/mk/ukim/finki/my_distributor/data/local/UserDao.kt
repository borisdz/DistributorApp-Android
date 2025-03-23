package mk.ukim.finki.my_distributor.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mk.ukim.finki.my_distributor.domain.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("select * from users where id = :userId limit 1")
    suspend fun getUser(userId: Long): UserEntity?

}