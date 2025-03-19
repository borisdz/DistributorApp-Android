package mk.ukim.finki.my_distributor.domain.dto

data class OrderSubmission(
    val orderItems: List<OrderItem>,
    val proForma: Boolean
)
