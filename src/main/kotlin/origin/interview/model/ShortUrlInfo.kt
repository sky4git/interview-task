package origin.interview.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortUrlInfo(
    // short url i.e. http://short.ly/abC1X2
    val shortUrl: String,
    // original full url
    val fullUrl: String,
    // number of clicks on the short url so far
    val clicks: Int,
)
