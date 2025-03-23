package mk.ukim.finki.my_distributor.data.api.service

import mk.ukim.finki.my_distributor.domain.dto.OrderDetailDto
import mk.ukim.finki.my_distributor.domain.dto.OrderSubmission
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApiService {
    @POST("api/order/create")
    suspend fun submitOrder(@Body orderSubmission: OrderSubmission): Response<Unit>

    @GET("api/order/{orderId}")
    suspend fun getOrder(@Path("orderId") orderId: Long): Response<OrderDetailDto>
}