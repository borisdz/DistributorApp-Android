package mk.ukim.finki.my_distributor.domain.dto

import java.math.BigDecimal

data class ArticleDto (
    val id: Long,
    val name: String,
    val manufacturer: String,
    val quantity: Int,
    val manufacturerId: Long,
    val price: BigDecimal,
    val category: String,
    val categoryId: Int,
    val weight: Int,
    val image: String
)