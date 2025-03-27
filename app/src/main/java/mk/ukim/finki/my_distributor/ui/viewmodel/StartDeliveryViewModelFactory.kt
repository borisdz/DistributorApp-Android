package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository

class StartDeliveryViewModelFactory(
    private val repository: DeliveryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartDeliveryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StartDeliveryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}