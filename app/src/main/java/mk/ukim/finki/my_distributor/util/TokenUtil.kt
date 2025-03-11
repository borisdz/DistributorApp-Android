package mk.ukim.finki.my_distributor.util

import com.auth0.android.jwt.JWT

data class DecodedToken(
    val email: String?,
    val roles: List<String>
)

fun decodeJwtToken(token: String): DecodedToken {
    val jwt = JWT(token)

    val email = jwt.subject

    val roles = jwt.getClaim("roles").asList(String::class.java) ?: emptyList()
    return DecodedToken(email,roles)
}