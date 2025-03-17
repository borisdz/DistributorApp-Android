package mk.ukim.finki.my_distributor.domain.dto

import mk.ukim.finki.my_distributor.domain.enumerations.PaymentMethod

data class OrderSubmission(
    val items: List<OrderItem>,
    val paymentMethod: PaymentMethod
)
