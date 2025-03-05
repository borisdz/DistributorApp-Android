package mk.ukim.finki.my_distributor.domain

data class CustomerDashboardData(
    val orders: List<OrderDto>,
    val deliveries: List<DeliveryDto>,
    val proFormas: List<ProFormaDto>
)
