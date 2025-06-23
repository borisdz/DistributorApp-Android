package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.ManagerRepository
import mk.ukim.finki.my_distributor.domain.dto.CreateDeliveryResponseDto
import mk.ukim.finki.my_distributor.domain.dto.OrderSimpleDto
import mk.ukim.finki.my_distributor.domain.dto.VehicleDto

class CreateDeliveryViewModelFactory(
    private val repository: ManagerRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CreateDeliveryViewModel::class.java)) {
            CreateDeliveryViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

class CreateDeliveryViewModel(
    private val repo: ManagerRepository
) : ViewModel() {

    val unassignedOrders = MutableLiveData<List<OrderSimpleDto>>()
    val vehicles         = MutableLiveData<List<VehicleDto>>()
    val selectedOrders   = mutableSetOf<Long>()
    val selectedVehicle  = MutableLiveData<VehicleDto?>()
    val deliveryDate     = MutableLiveData<String>("")

    private val _result = MutableLiveData<CreateDeliveryResponseDto>()
    val result: LiveData<CreateDeliveryResponseDto> = _result

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadData() {
        viewModelScope.launch {
            repo.fetchUnassignedOrders()
                .onSuccess { unassignedOrders.value = it }
                .onFailure { _error.value = it.message }
            repo.fetchVehicles()
                .onSuccess { vehicles.value = it }
                .onFailure { _error.value = it.message }
        }
    }

    fun toggleOrderSelection(order: OrderSimpleDto) {
        if (!selectedOrders.remove(order.id))
            selectedOrders.add(order.id)
        unassignedOrders.value = unassignedOrders.value
    }

    fun createDelivery() {
        val vehicleId   = selectedVehicle.value?.id ?: run {
            _error.value = "Select a vehicle"; return
        }
        val date = deliveryDate.value ?: run {
            _error.value = "Select date"; return
        }
        viewModelScope.launch {
            repo.postCreateDelivery(date, vehicleId, selectedOrders.toList())
                .onSuccess { _result.value = it }
                .onFailure { _error.value = it.message }
        }
    }
}