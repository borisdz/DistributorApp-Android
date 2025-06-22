package mk.ukim.finki.my_distributor.domain.dto

data class DeliveryWithOrdersDto(
    val delivery: DeliveryDto,
    val orders: List<OrderDeliveryDto>
)
