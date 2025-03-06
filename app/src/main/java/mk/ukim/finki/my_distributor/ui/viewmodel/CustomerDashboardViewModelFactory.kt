package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.my_distributor.data.repository.CustomerRepository

class CustomerDashboardViewModelFactory(
    private val repository: CustomerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CustomerDashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerDashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}