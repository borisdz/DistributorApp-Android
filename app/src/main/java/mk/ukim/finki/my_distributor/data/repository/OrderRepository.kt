package mk.ukim.finki.my_distributor.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mk.ukim.finki.my_distributor.data.api.OrderApiService
import mk.ukim.finki.my_distributor.domain.dto.OrderSubmission

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
}