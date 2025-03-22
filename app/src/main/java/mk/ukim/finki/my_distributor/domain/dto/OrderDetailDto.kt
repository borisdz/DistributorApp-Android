package mk.ukim.finki.my_distributor.domain.dto

data class OrderDetailDto(
    val order: OrderDto,
    val items: List<ArticleDto>
)