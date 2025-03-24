package mk.ukim.finki.my_distributor.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mk.ukim.finki.my_distributor.data.api.service.DashboardApiService
import mk.ukim.finki.my_distributor.data.api.service.DeliveryApiService
import mk.ukim.finki.my_distributor.domain.dto.DeliveryDto

class DeliveryRepository(
    private val dashboardApiService: DeliveryApiService
) {
    suspend fun getDriverDeliveries(): Result<List<DeliveryDto>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = dashboardApiService.getDriverNewDeliveries()
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
}