package mk.ukim.finki.my_distributor.data.repository

import mk.ukim.finki.my_distributor.data.api.AuthApiService
import mk.ukim.finki.my_distributor.domain.LoginRequestDto
import mk.ukim.finki.my_distributor.domain.LoginResponseDto
import mk.ukim.finki.my_distributor.domain.UserDto

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