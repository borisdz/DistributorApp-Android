package mk.ukim.finki.my_distributor.data.local

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    @OptIn(InternalCoroutinesApi::class)
    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "my_distributor_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}