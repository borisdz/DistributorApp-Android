package mk.ukim.finki.my_distributor.domain.dto

data class OrderDetailDto(
    val order: OrderSimpleDto,
    val items: List<ArticleDto>
)