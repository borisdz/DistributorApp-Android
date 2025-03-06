package mk.ukim.finki.my_distributor.data.api

import mk.ukim.finki.my_distributor.domain.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface UserApiService {
    @GET
    suspend fun getUserByEmail(@Body email: String): Response<UserDto>
}