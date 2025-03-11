package mk.ukim.finki.my_distributor.data.api;

import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.util.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://10.0.2.2:8443/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    fun getOkHttpClient(userPreferences: UserPreferences) : OkHttpClient {
        return getUnsafeOkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor(userPreferences))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun getRetrofit(userPreferences: UserPreferences): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(userPreferences))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getUserApiService(userPreferences: UserPreferences): UserApiService {
        return getRetrofit(userPreferences).create(UserApiService::class.java)
    }

    fun getAuthApiService(userPreferences: UserPreferences): AuthApiService {
        return getRetrofit(userPreferences).create(AuthApiService::class.java)
    }

    fun getDashboardApiService(userPreferences: UserPreferences): DashboardApiService {
        return getRetrofit(userPreferences).create(DashboardApiService::class.java)
    }
}