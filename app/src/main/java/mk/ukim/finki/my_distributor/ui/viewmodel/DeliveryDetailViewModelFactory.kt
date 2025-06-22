package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository

class DeliveryDetailViewModelFactory(
    private val repo: DeliveryRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(cls: Class<T>): T {
        if(cls.isAssignableFrom(DeliveryDetailViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DeliveryDetailViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM")
    }
}