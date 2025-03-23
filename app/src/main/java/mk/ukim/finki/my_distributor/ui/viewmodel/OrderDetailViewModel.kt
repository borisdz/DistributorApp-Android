package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.OrderRepository
import mk.ukim.finki.my_distributor.domain.dto.OrderDetailDto
import mk.ukim.finki.my_distributor.domain.dto.OrderDto

class OrderDetailViewModel(
    private val repository: OrderRepository
) : ViewModel() {
    private val _orderDetail = MutableLiveData<OrderDetailDto?>()
    val orderDetail: LiveData<OrderDetailDto?> get() = _orderDetail

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadOrderDetail(orderId: Long){
        viewModelScope.launch {
            val result = repository.getOrder(orderId)
            result.onSuccess { detail ->
                _orderDetail.value = detail
            }.onFailure { ex ->
                _error.value = ex.message ?: "An error has occuerred."
            }
        }
    }
}