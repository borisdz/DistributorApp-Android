package mk.ukim.finki.my_distributor.domain.dto

data class LoginResponseDto(
    val userId: Long,
    val userName: String,
    val userPassword: String,
    val userEmail: String,
    val userMobile: String,
    val userSalt: String,
    val userActive: Boolean,
    val userImage: String,
    val userRole: String,
    val clazz_: String
)
