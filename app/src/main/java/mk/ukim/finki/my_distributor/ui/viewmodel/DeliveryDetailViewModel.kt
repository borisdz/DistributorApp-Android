package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository
import mk.ukim.finki.my_distributor.domain.dto.DeliveryWithOrdersDto

class DeliveryDetailViewModel(
    private val repo: DeliveryRepository
) : ViewModel() {

    private val _detail = MutableLiveData<DeliveryWithOrdersDto?>()
    val detail: LiveData<DeliveryWithOrdersDto?> = _detail

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun load(deliveryId: Long){
        viewModelScope.launch {
            repo.getDeliveryWithOrders(deliveryId)
                .onSuccess { _detail.value = it}
                .onFailure { _error.value = it.message ?: "Unknown error" }
        }
    }
}