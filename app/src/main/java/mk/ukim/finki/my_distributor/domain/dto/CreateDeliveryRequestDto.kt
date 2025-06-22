package mk.ukim.finki.my_distributor.domain.dto

data class CreateDeliveryRequestDto(
    val deliveryDate: String,
    val vehicleId: Int,
    val orderIds: List<Long>
)
