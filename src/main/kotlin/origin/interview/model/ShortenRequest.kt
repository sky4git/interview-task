package origin.interview.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortenRequest(
    val url: String,
)
