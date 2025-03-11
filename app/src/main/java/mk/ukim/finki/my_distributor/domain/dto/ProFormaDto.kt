package mk.ukim.finki.my_distributor.domain.dto

import java.time.LocalDate

data class ProFormaDto(
    val id: Long,
    val pfDeadline: LocalDate,
    val pfDateCreated: LocalDate,
    val statusId: Short,
    val statusName: String,
    val ordId: Long,
    val customerId: Long,
    val customerName: String,
    val customerEmail: String,
    val customerPhone: String
)
