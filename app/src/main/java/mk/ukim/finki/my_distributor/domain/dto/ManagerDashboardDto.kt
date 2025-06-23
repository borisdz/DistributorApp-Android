package mk.ukim.finki.my_distributor.domain.dto

data class ManagerDashboardDto(
    val newOrders: List<OrderSimpleDto>,
    val pendingDeliveries: List<DeliverySimpleDto>
)
