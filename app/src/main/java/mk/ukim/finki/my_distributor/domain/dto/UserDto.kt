package mk.ukim.finki.my_distributor.domain.dto

data class UserDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val image: String,
    val cityId: Int,
    val cityName: String,
    val regionName: String,
    val role: String,
    val userActive: Boolean
)
