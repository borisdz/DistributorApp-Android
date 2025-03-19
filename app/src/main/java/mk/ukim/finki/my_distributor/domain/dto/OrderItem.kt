package mk.ukim.finki.my_distributor.domain.dto

data class OrderItem(
    val article: ArticleDto,
    var quantity: Int
)
