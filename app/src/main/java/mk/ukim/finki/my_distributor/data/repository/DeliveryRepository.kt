package mk.ukim.finki.my_distributor.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mk.ukim.finki.my_distributor.data.api.service.DeliveryApiService
import mk.ukim.finki.my_distributor.domain.dto.DeliverySimpleDto
import mk.ukim.finki.my_distributor.domain.dto.DeliveryWithOrdersDto

class DeliveryRepository(
    private val apiService: DeliveryApiService
) {

    suspend fun getDriverDeliveries(): Result<List<DeliverySimpleDto>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getDriverNewDeliveries()
                if (response.isSuccessful) {
                    val deliveries = response.body() ?: emptyList()
                    Result.success(deliveries)
                } else {
                    Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
                }
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }
    }

    suspend fun getDeliveryWithOrders(deliveryId: Long): Result<DeliveryWithOrdersDto> =
        withContext(Dispatchers.IO) {
            try {
                val resp = apiService.getDeliveryWithOrders(deliveryId)
                if (resp.isSuccessful) {
                    resp.body()?.let { Result.success(it) }
                        ?: Result.failure(Exception("Empty delivery data"))
                } else {
                    Result.failure(Exception("Error ${resp.code()}: ${resp.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}