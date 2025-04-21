package origin.interview.service.model

data class ShortUrlMetadata(
    val shortUrl: String,
    val originalUrl: String,
    val clicks: Int,
)
