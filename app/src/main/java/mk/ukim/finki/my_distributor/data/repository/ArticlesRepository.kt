package mk.ukim.finki.my_distributor.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mk.ukim.finki.my_distributor.data.api.service.ArticleApiService
import mk.ukim.finki.my_distributor.domain.dto.ArticleDto

class ArticlesRepository(
    private val articleApiService: ArticleApiService
) {

    suspend fun getArticles(): Result<List<ArticleDto>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = articleApiService.getArticles()
                if (response.isSuccessful) {
                    val articles = response.body() ?: emptyList()
                    Result.success(articles)
                } else {
                    Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}