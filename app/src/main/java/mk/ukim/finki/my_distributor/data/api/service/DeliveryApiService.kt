package mk.ukim.finki.my_distributor.data.api.service

import mk.ukim.finki.my_distributor.domain.dto.DeliveryDto
import mk.ukim.finki.my_distributor.domain.dto.DeliveryWithOrdersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DeliveryApiService {

    @GET("api/driver/newDeliveries")
    suspend fun getDriverNewDeliveries(): Response<List<DeliveryDto>>

    @GET("api/delivery/delivery-with-orders/{deliveryId}")
    suspend fun getDeliveryWithOrders(@Path("deliveryId") deliveryId: Long): Response<DeliveryWithOrdersDto>
}