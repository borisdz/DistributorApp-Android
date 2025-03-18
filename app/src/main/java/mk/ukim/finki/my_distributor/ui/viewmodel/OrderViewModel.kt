package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.OrderRepository
import mk.ukim.finki.my_distributor.domain.dto.OrderItem
import mk.ukim.finki.my_distributor.domain.dto.OrderSubmission
import mk.ukim.finki.my_distributor.domain.enumerations.PaymentMethod

class OrderViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _orderItems = MutableLiveData<List<OrderItem>>(emptyList())
    val orderItems: LiveData<List<OrderItem>> get() = _orderItems

    private val _orderSubmissionResult = MutableLiveData<Result<Unit>>()
    val orderSubmissionResult: LiveData<Result<Unit>> get() = _orderSubmissionResult

    fun addItem(orderItem: OrderItem) {
        val current = _orderItems.value?.toMutableList() ?: mutableListOf()

        val index = current.indexOfFirst { it.article.id == orderItem.article.id }
        if (index != -1) {
            val existing = current[index]
            current[index] = existing.copy(quantity = existing.quantity + orderItem.quantity)
        } else {
            current.add(orderItem)
        }
        _orderItems.value = current
    }

    fun updateItem(orderItem: OrderItem){
        val current = _orderItems.value?.toMutableList() ?: mutableListOf()
        val index = current.indexOfFirst { it.article.id == orderItem.article.id }
        if(index != -1){
            current[index] = orderItem
            _orderItems.value = current
        }
    }

    fun clearOrder() {
        _orderItems.value = emptyList()
    }

    fun completeOrder(paymentMethod: PaymentMethod){
        val submission = OrderSubmission(
            items = _orderItems.value ?: emptyList(),
            paymentMethod = paymentMethod
        )
        viewModelScope.launch {
            val result = orderRepository.submitOrder(submission)
            _orderSubmissionResult.value = result
        }
    }

    fun removeItem(orderItem: OrderItem){
        val current = _orderItems.value?.toMutableList() ?: mutableListOf()
        current.removeAll { it.article.id == orderItem.article.id }
        _orderItems.value = current
    }
}