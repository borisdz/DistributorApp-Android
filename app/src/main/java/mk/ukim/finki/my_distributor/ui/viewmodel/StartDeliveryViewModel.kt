package mk.ukim.finki.my_distributor.ui.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository
import mk.ukim.finki.my_distributor.domain.dto.OrderDeliveryDto

class StartDeliveryViewModel(
    private val repo: DeliveryRepository
) : ViewModel() {

    // Backing list of stops/orders
    private val _orders = MutableLiveData<List<OrderDeliveryDto>>(emptyList())
    val orders: LiveData<List<OrderDeliveryDto>> = _orders

    // Any errors
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // The current next-stop Location
    private val _currentStopLocation = MutableLiveData<Location?>()
    val currentStopLocation: LiveData<Location?> = _currentStopLocation

    /** Loads the delivery and its orders, then sets up the first stop’s location. */
    fun loadDeliveryWithOrders(id: Long) = viewModelScope.launch {
        repo.getDeliveryWithOrders(id)
            .onSuccess { dto ->
                _orders.value = dto.orders
                updateCurrentLocation()
            }
            .onFailure { ex ->
                _error.value = ex.message
            }
    }

    /** Reorders the stops list, then refreshes the first-stop location. */
    fun reorderOrders(from: Int, to: Int) {
        val list = _orders.value?.toMutableList() ?: return
        val item = list.removeAt(from)
        list.add(to, item)
        _orders.value = list
        updateCurrentLocation()
    }

    /** Call this when the driver returns from navigating to the current stop. */
    fun markCurrentStopDone() {
        val list = _orders.value?.toMutableList() ?: return
        if (list.isNotEmpty()) {
            list.removeAt(0)
            _orders.value = list
            updateCurrentLocation()
        }
    }

    /** Records the starting kilometers — you can expand this to POST to your API. */
    fun startDelivery(km: Int) {
        // TODO: Save km in local state or send to backend
    }

    /** Internal: gets the first order’s lat/lng and posts a Location, or null if none. */
    private fun updateCurrentLocation() {
        val first = _orders.value?.firstOrNull()
        if (first != null) {
            val loc = Location("app").apply {
                latitude = first.latitude.toDouble()
                longitude = first.longitude.toDouble()
            }
            _currentStopLocation.value = loc
        } else {
            _currentStopLocation.value = null
        }
    }
}
