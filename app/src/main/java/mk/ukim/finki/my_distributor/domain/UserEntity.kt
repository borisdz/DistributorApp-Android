package mk.ukim.finki.my_distributor.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val id: Long,
    val name: String,
    val token: String
)