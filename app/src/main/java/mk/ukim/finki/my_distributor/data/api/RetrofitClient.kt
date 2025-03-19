package mk.ukim.finki.my_distributor.data.api;

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import mk.ukim.finki.my_distributor.data.api.service.ArticleApiService
import mk.ukim.finki.my_distributor.data.api.service.AuthApiService
import mk.ukim.finki.my_distributor.data.api.service.DashboardApiService
import mk.ukim.finki.my_distributor.data.api.service.OrderApiService
import mk.ukim.finki.my_distributor.data.api.service.UserApiService
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.util.AuthInterceptor
import mk.ukim.finki.my_distributor.util.LocalDateTimeAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

object RetrofitClient {
    private const val BASE_URL = "https://10.0.2.2:8443/"

    @SuppressLint("NewApi")
    val customGson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun getOkHttpClient(userPreferences: UserPreferences): OkHttpClient {
        return getUnsafeOkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor(userPreferences))
            .addInterceptor(loggingInterceptor)
            .followRedirects(false)
            .build()
    }

    private fun getRetrofit(userPreferences: UserPreferences): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(userPreferences))
            .addConverterFactory(GsonConverterFactory.create(customGson))
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

    fun getArticleApiService(userPreferences: UserPreferences): ArticleApiService {
        return getRetrofit(userPreferences).create(ArticleApiService::class.java)
    }

    fun getOrderApiService(userPreferences: UserPreferences): OrderApiService {
        return getRetrofit(userPreferences).create(OrderApiService::class.java)
    }
}