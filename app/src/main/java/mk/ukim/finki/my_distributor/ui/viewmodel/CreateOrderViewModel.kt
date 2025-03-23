package mk.ukim.finki.my_distributor.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.my_distributor.data.repository.ArticlesRepository
import mk.ukim.finki.my_distributor.domain.dto.ArticleDto

class CreateOrderViewModel(
    private val articlesRepository: ArticlesRepository
) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleDto>>()
    val articles: LiveData<List<ArticleDto>> get() = _articles

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var allArticles: List<ArticleDto> = emptyList()

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            articlesRepository.getArticles()
                .onSuccess { fetchedArticles ->
                    allArticles = fetchedArticles
                    _articles.postValue(fetchedArticles)
                }
                .onFailure { exception ->
                    _error.postValue(exception.message)
                }
        }
    }

    fun searchArticles(query: String) {
        val filtered = allArticles.filter { article ->
            article.name.contains(query, ignoreCase = true)
        }
        _articles.postValue(filtered)
    }
}