package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.CustomerRepository
import mk.ukim.finki.my_distributor.domain.dto.CustomerDashboardData
import mk.ukim.finki.my_distributor.util.Resource

class CustomerDashboardViewModel(
    private val repository: CustomerRepository
) : ViewModel() {

    private val _dashboardData = MutableLiveData<Resource<CustomerDashboardData>>()
    val dashboardData: LiveData<Resource<CustomerDashboardData>> = _dashboardData

    fun loadDashboardData(customerId: Long) {
        _dashboardData.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.fetchDashboardData(customerId)
            _dashboardData.value = result
        }
    }
}