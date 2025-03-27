package mk.ukim.finki.my_distributor.ui.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository
import mk.ukim.finki.my_distributor.domain.dto.OrderDto
import mk.ukim.finki.my_distributor.domain.dto.OrderItem

class StartDeliveryViewModel(
    private val repository: DeliveryRepository
) : ViewModel() {

    private val _deliveryOrders = MutableLiveData<List<OrderDto>>(emptyList())
    val deliveryOrders: LiveData<List<OrderDto>> get() = _deliveryOrders

    private val _startKm = MutableLiveData<Int>()
    val startKm: LiveData<Int> get() = _startKm

    private val _firstOrderLocation = MutableLiveData<Location?>()
    val firstOrderLocation: LiveData<Location?> get() = _firstOrderLocation

    fun loadAssignedOrders(){
        viewModelScope.launch {
            repository.getDriverDeliveries().onSuccess { delivery ->
                _deliveryOrders.value =

            }
        }
    }
}