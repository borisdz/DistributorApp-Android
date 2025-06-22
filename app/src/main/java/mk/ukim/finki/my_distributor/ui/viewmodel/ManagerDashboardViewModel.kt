package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.ManagerRepository
import mk.ukim.finki.my_distributor.domain.dto.ManagerDashboardDto

class ManagerDashboardViewModelFactory(
    private val repository: ManagerRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ManagerDashboardViewModel::class.java)) {
            ManagerDashboardViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

class ManagerDashboardViewModel(
    private val repo: ManagerRepository
) : ViewModel() {

    private val _dashboard = MutableLiveData<ManagerDashboardDto>()
    val dashboard: LiveData<ManagerDashboardDto> = _dashboard

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadDashboard() {
        viewModelScope.launch {
            repo.fetchDashboard()
                .onSuccess { _dashboard.value = it }
                .onFailure { _error.value = it.message }
        }
    }
}
