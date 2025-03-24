package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository
import mk.ukim.finki.my_distributor.domain.dto.DeliveryDto

class DriverDashboardViewModel(
    private val repository: DeliveryRepository
) : ViewModel() {

    private val _deliveries = MutableLiveData<List<DeliveryDto>>()
    val deliveries: LiveData<List<DeliveryDto>> get() = _deliveries

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadDriverDeliveries(){
        viewModelScope.launch {
            repository.getDriverDeliveries().onSuccess { list ->
                _deliveries.value = list.sortedBy { it.delDate }
            }.onFailure { ex ->
                _error.value = ex.message ?: "An error occurred"
            }
        }
    }
}