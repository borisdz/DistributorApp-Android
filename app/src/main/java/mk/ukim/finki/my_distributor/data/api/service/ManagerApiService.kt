package mk.ukim.finki.my_distributor.data.api.service

import mk.ukim.finki.my_distributor.domain.dto.CreateDeliveryRequestDto
import mk.ukim.finki.my_distributor.domain.dto.CreateDeliveryResponseDto
import mk.ukim.finki.my_distributor.domain.dto.ManagerDashboardDto
import mk.ukim.finki.my_distributor.domain.dto.OrderDto
import mk.ukim.finki.my_distributor.domain.dto.VehicleDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ManagerApiService {
    @GET("api/manager/dashboard")
    suspend fun getDashboard(): Response<ManagerDashboardDto>

    @GET("api/manager/orders/unassigned")
    suspend fun getUnassignedOrders(): Response<List<OrderDto>>

    @GET("api/manager/vehicles")
    suspend fun getVehicles(): Response<List<VehicleDto>>

    @POST("api/manager/deliveries")
    suspend fun createDelivery(
        @Body body: CreateDeliveryRequestDto
    ): Response<CreateDeliveryResponseDto>
}