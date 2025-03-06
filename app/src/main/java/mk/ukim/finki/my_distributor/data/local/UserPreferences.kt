package mk.ukim.finki.my_distributor.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import mk.ukim.finki.my_distributor.domain.dto.LoginResponseDto

class UserPreferences(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "user_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveUser(user: LoginResponseDto) {
        sharedPreferences.edit().apply {
            putLong("userId", user.userId)
            putString("userName", user.userName)
            putString("userEmail", user.userEmail)
            putString("userMobile", user.userMobile)
            putBoolean("userActive", user.userActive)
            putString("userImage", user.userImage)
            putString("userRole", user.userRole)
            putString("clazz_", user.clazz_)

            apply()
        }
    }

    fun getUser(): LoginResponseDto? {
        val userId = sharedPreferences.getLong("userId",0)
        if (userId == 0L) return null
        return LoginResponseDto(
            userId = userId,
            userName = sharedPreferences.getString("userName", "") ?: "",
            userPassword = "", // Do not expose the password.
            userEmail = sharedPreferences.getString("userEmail", "") ?: "",
            userMobile = sharedPreferences.getString("userMobile", "") ?: "",
            userSalt = "", // Do not expose the salt.
            userActive = sharedPreferences.getBoolean("userActive", false),
            userImage = sharedPreferences.getString("userImage", "") ?: "",
            userRole = sharedPreferences.getString("userRole", "") ?: "",
            clazz_ = sharedPreferences.getString("clazz_", "") ?: ""
        )
    }

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }
}