package mk.ukim.finki.my_distributor.ui.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository
import mk.ukim.finki.my_distributor.domain.dto.DeliveryWithOrdersDto

class StartDeliveryViewModel(
    private val repository: DeliveryRepository
) : ViewModel() {

    private val _deliveryWithOrders = MutableLiveData<DeliveryWithOrdersDto?>()
    val deliveryWithOrders: LiveData<DeliveryWithOrdersDto?> get() = _deliveryWithOrders

    private val _startKm = MutableLiveData<Int>()
    val startKm: LiveData<Int> get() = _startKm

    private val _firstOrderLocation = MutableLiveData<Location?>()
    val firstOrderLocation: LiveData<Location?> get() = _firstOrderLocation

    fun loadDelivery(deliveryId: Long){
        viewModelScope.launch {
            repository.getDeliveryWithOrders(deliveryId).onSuccess { dto ->
                _deliveryWithOrders.value = dto

                if(dto.orders.isNotEmpty()){
                    val firstOrder = dto.orders.first()
                }
            }.onFailure { ex ->
                //
            }
        }
    }

    fun startDelivery(startKm: Int){
        _startKm.value  = startKm
    }

    fun reorderOrders(fromPosition: Int, toPosition: Int){
        _deliveryWithOrders.value?.let { dto ->
            val currentOrders = dto.orders.toMutableList()
            val order = currentOrders.removeAt(fromPosition)
            currentOrders.add(toPosition,order)
            _deliveryWithOrders.value = dto.copy(orders = currentOrders)
        }
    }
}