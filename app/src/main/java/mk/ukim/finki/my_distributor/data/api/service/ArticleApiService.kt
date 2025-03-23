package mk.ukim.finki.my_distributor.data.api.service

import mk.ukim.finki.my_distributor.domain.dto.ArticleDto
import retrofit2.Response
import retrofit2.http.GET

interface ArticleApiService {

    @GET("/api/customer/articles")
    suspend fun getArticles(): Response<List<ArticleDto>>
}