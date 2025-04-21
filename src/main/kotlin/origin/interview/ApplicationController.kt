package origin.interview

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import origin.interview.model.ShortUrlInfo
import origin.interview.model.ShortenResponse
import origin.interview.service.UrlShortenerService

@RestController
@RequestMapping("/api")
class ApplicationController(private val urlShortenerService: UrlShortenerService) {

    @GetMapping("/shorten", produces = ["application/json"])
    fun shortenURL(@RequestParam url: String): ShortenResponse {
        val shortURL = urlShortenerService.shortenURL(url)
        return ShortenResponse(shortUrl = shortURL.toString())
    }

    @GetMapping("/redirect/{shortCode}")
    fun redirect(@PathVariable shortCode: String): ResponseEntity<Unit> {
        val fullURI = urlShortenerService.getFullUrlFromShortcode(shortCode)
        return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY.value()).location(fullURI).build()
    }

    @PostMapping("/info/{shortCode}", produces = ["application/json"])
    fun info(@PathVariable shortCode: String): ShortUrlInfo {
        val shortUrlMetadata = urlShortenerService.getInfoByShortcode(shortCode)
        return ShortUrlInfo(
            shortUrl = shortUrlMetadata.shortUrl,
            fullUrl = shortUrlMetadata.originalUrl,
            clicks = shortUrlMetadata.clicks,
        )
    }
}