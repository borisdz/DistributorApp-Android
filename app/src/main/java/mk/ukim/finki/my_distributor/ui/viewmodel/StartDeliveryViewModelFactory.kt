package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository

class StartDeliveryViewModelFactory(
    private val repo: DeliveryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(c: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return StartDeliveryViewModel(repo) as T
    }
}
