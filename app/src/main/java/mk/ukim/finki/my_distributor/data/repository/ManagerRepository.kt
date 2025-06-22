package mk.ukim.finki.my_distributor.data.repository

import mk.ukim.finki.my_distributor.data.api.service.ManagerApiService
import mk.ukim.finki.my_distributor.domain.dto.CreateDeliveryRequestDto
import retrofit2.HttpException

class ManagerRepository(private val api: ManagerApiService) {
    suspend fun fetchDashboard() = runCatching {
        val resp = api.getDashboard()
        if (!resp.isSuccessful) throw HttpException(resp)
        resp.body()!!
    }

    suspend fun fetchUnassignedOrders() = runCatching {
        val resp = api.getUnassignedOrders()
        if (!resp.isSuccessful) throw HttpException(resp)
        resp.body()!!
    }

    suspend fun fetchVehicles() = runCatching {
        val resp = api.getVehicles()
        if (!resp.isSuccessful) throw HttpException(resp)
        resp.body()!!
    }

    suspend fun postCreateDelivery(
        date: String,
        vehicleId: Int,
        orderIds: List<Long>
    ) = runCatching {
        val resp = api.createDelivery(
            CreateDeliveryRequestDto(date, vehicleId, orderIds)
        )
        if (!resp.isSuccessful) throw HttpException(resp)
        resp.body()!!
    }
}