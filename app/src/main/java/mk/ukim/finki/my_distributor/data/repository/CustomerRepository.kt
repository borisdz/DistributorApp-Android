package mk.ukim.finki.my_distributor.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mk.ukim.finki.my_distributor.data.api.DashboardApiService
import mk.ukim.finki.my_distributor.data.local.DashboardDataDao
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.domain.DashboardDataEntity
import mk.ukim.finki.my_distributor.domain.dto.CustomerDashboardData
import mk.ukim.finki.my_distributor.util.Resource

class CustomerRepository(
    private val dashboardApiService: DashboardApiService,
    private val dashboardDataDao: DashboardDataDao,
    private val gson: Gson = Gson()
//    private val userPreferences: UserPreferences
) {

    suspend fun fetchDashboardData(customerId: Long): Resource<CustomerDashboardData> {
//        val user = userPreferences.getUser() ?: return Resource.Error("No logged-in user found")
//
//        val customerId = user.userId

        return withContext(Dispatchers.IO) {
            try {
                val response = dashboardApiService.getDashboardData(customerId)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        val jsonData = gson.toJson(data)
                        dashboardDataDao.insertDashboardData(
                            DashboardDataEntity(
                                customerId,
                                jsonData,
                                System.currentTimeMillis()
                            )
                        )
                        Resource.Success(data)
                    } else {
                        Resource.Error("No data available")
                    }
                } else {
                    val cachedDataEntity = dashboardDataDao.getDashboardData(customerId)
                    if (cachedDataEntity != null) {
                        val cachedData = gson.fromJson(
                            cachedDataEntity.jsonData,
                            CustomerDashboardData::class.java
                        )
                        Resource.Success(cachedData)
                    } else {
                        Resource.Error("Error: ${response.code()} ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                val cachedDataEntity = dashboardDataDao.getDashboardData(customerId)
                if (cachedDataEntity != null) {
                    val cachedData = gson.fromJson(
                        cachedDataEntity.jsonData,
                        CustomerDashboardData::class.java
                    )
                    Resource.Success(cachedData)
                } else {
                    Resource.Error(e.localizedMessage ?: "An unknown error occurred")
                }
            }
        }
    }
}