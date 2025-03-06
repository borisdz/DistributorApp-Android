package mk.ukim.finki.my_distributor.data.api

import mk.ukim.finki.my_distributor.domain.dto.LoginRequestDto
import mk.ukim.finki.my_distributor.domain.dto.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequestDto): Response<LoginResponseDto>
}