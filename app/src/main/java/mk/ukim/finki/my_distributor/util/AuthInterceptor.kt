package mk.ukim.finki.my_distributor.util

import mk.ukim.finki.my_distributor.data.local.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userPreferences: UserPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = userPreferences.getToken()

        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        if (!token.isNullOrEmpty()) {
            builder.header("Authorization", "Bearer $token")
        }

        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}