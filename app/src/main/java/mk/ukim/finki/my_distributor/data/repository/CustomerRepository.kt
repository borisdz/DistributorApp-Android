package mk.ukim.finki.my_distributor.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mk.ukim.finki.my_distributor.data.api.DashboardApiService
import mk.ukim.finki.my_distributor.domain.CustomerDashboardData
import mk.ukim.finki.my_distributor.util.Resource

class CustomerRepository(private val dashboardApiService: DashboardApiService) {

    suspend fun fetchDashboardData(customerId: Long): Resource<CustomerDashboardData> {
        return withContext(Dispatchers.IO) {
            try {
                val response = dashboardApiService.getDashboardData(customerId)
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null) {
                        Resource.Success(data)
                    } else {
                        Resource.Error("No data available")
                    }
                } else {
                    Resource.Error("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unknown error occurred")
            }
        }
    }
}