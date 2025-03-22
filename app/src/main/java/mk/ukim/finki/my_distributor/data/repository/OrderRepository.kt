package mk.ukim.finki.my_distributor.data.repository

import android.util.Log.e
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mk.ukim.finki.my_distributor.data.api.service.OrderApiService
import mk.ukim.finki.my_distributor.domain.dto.OrderDetailDto
import mk.ukim.finki.my_distributor.domain.dto.OrderDto
import mk.ukim.finki.my_distributor.domain.dto.OrderSubmission
import mk.ukim.finki.my_distributor.ui.fragments.customer.OrderDetailFragment

class OrderRepository(
    private val orderApiService: OrderApiService
) {

    suspend fun submitOrder(orderSubmission: OrderSubmission): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = orderApiService.submitOrder(orderSubmission)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(
                        Exception(
                            "Error: ${response.code()} ${response.message()}"
                        )
                    )
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getOrder(orderId: Long): Result<OrderDetailDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = orderApiService.getOrder(orderId)
                if (response.isSuccessful) {
                    val orderDetail = response.body()
                    if (orderDetail != null) {
                        Result.success(orderDetail)
                    } else {
                        Result.failure(Exception("Order detail is null"))
                    }
                } else {
                    Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}