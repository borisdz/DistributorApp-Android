package mk.ukim.finki.my_distributor.data.repository

import mk.ukim.finki.my_distributor.data.api.UserApiService
import mk.ukim.finki.my_distributor.domain.dto.UserDto

class UserRepository(private val userApiService: UserApiService) {
    suspend fun getUserByEmail(email: String): Result<UserDto> {
        return try{
            val response = userApiService.getUserByEmail(email)
            if(response.isSuccessful && response.body()!=null){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Fetching User data failed: ${response.code()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}