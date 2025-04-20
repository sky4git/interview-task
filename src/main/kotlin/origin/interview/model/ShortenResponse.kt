package origin.interview.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortenResponse(
    val shortenUrl: String,
)
