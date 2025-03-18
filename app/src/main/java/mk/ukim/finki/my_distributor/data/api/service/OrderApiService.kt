package mk.ukim.finki.my_distributor.data.api.service

import mk.ukim.finki.my_distributor.domain.dto.OrderSubmission
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApiService {
    @POST("api/orders")
    suspend fun submitOrder(@Body orderSubmission: OrderSubmission): Response<Unit>
}