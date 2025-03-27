package mk.ukim.finki.my_distributor.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mk.ukim.finki.my_distributor.data.api.service.DashboardApiService
import mk.ukim.finki.my_distributor.data.api.service.DeliveryApiService
import mk.ukim.finki.my_distributor.domain.dto.DeliveryDto
import mk.ukim.finki.my_distributor.domain.dto.DeliveryWithOrdersDto

class DeliveryRepository(
    private val apiService: DeliveryApiService
) {

    suspend fun getDriverDeliveries(): Result<List<DeliveryDto>> {
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

    suspend fun getDeliveryWithOrders(deliveryId: Long): Result<DeliveryWithOrdersDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getDeliveryWithOrders(deliveryId)
                if (response.isSuccessful) {
                    val dto = response.body()
                    if (dto != null) {
                        Result.success(dto)
                    } else {
                        Result.failure(Exception("No delivery data found"))
                    }
                } else {
                    Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
                }
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }
    }
}