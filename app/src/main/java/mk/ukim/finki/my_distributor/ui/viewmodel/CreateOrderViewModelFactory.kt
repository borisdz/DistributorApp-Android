package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.my_distributor.data.repository.ArticlesRepository

class CreateOrderViewModelFactory(
    private val articlesRepository: ArticlesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateOrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateOrderViewModel(articlesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}