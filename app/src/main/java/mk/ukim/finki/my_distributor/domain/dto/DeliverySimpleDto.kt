package mk.ukim.finki.my_distributor.domain.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class DeliverySimpleDto(
    @SerializedName("deliveryId")
    val deliveryId: Long,

    @SerializedName("driverName")
    val driverName: String,

    @SerializedName("deliveryDate")
    val deliveryDate: Date,

    @SerializedName("deliveryCreatedDate")
    val deliveryCreatedDate: Date,

    @SerializedName("deliveryStatus")
    val deliveryStatus: Short,

    @SerializedName("deliveryStatusName")
    val deliveryStatusName: String
)
