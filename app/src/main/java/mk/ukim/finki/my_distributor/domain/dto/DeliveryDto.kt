package mk.ukim.finki.my_distributor.domain.dto

import java.util.Date

data class DeliveryDto(
    val id: Long,
    val dateCreated: Date,
    val delDate: Date,
    val delStartKm: Int?,
    val delEndKm: Int?,
    val delStartTime: String?,
    val delEndTime: String?,
    val dStatusId: Short,
    val delStatus: String,
    val vehId: Long,
    val driverId: Long,
    val driverName: String,
    val driverImg: String
)
