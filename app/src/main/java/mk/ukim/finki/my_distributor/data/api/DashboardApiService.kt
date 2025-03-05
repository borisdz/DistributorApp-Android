package mk.ukim.finki.my_distributor.data.api

import mk.ukim.finki.my_distributor.domain.CustomerDashboardData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DashboardApiService {
    @GET("api/customer/dashboard")
    suspend fun getDashboardData(@Query("customerId") customerId: Long): Response<CustomerDashboardData>
}