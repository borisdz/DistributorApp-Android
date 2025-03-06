package mk.ukim.finki.my_distributor.data.repository

import mk.ukim.finki.my_distributor.data.api.AuthApiService
import mk.ukim.finki.my_distributor.domain.dto.LoginRequestDto
import mk.ukim.finki.my_distributor.domain.dto.LoginResponseDto

class AuthRepository(private val authApiService: AuthApiService) {
    suspend fun login(email: String, password: String): Result<LoginResponseDto> {
        return try {
            val response = authApiService.login(LoginRequestDto(email,password))
            if(response.isSuccessful && response.body()!=null){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}