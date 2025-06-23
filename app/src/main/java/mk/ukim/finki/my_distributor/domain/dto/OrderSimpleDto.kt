package mk.ukim.finki.my_distributor.domain.dto

import java.sql.Date
import java.time.LocalDateTime

data class OrderSimpleDto(
    val id: Long,
    val ordDate: Date,
    val ordSum: Int,
    val ordFulfillmentDate: LocalDateTime?,
    val ordComment: String?,
    val oStatusId: Short,
    val customerId: Long,
    val deliveryId: Long?,
    val pfId: Long?
)
