package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.my_distributor.data.repository.OrderRepository

class OrderViewModelFactory(
    private val orderRepository: OrderRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderViewModel(orderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}