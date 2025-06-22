package mk.ukim.finki.my_distributor.data.api.service

import mk.ukim.finki.my_distributor.domain.dto.CustomerDashboardData
import mk.ukim.finki.my_distributor.domain.dto.DriverDashboardData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardApiService {
    @GET("api/customer/{customerId}/dashboard")
    suspend fun getCustomerDashboardData(@Path("customerId") customerId: Long): Response<CustomerDashboardData>
}