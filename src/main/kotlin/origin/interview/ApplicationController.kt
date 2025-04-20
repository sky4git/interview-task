package origin.interview

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import origin.interview.model.ShortenResponse
import origin.interview.service.UrlShortenerService

@RestController
@RequestMapping("/api")
class ApplicationController(private val urlShortenerService: UrlShortenerService) {

    @GetMapping("/shorten", produces = ["application/json"])
    fun shortenURL(@RequestParam url: String): ShortenResponse {
        val shortURL = urlShortenerService.shortenURL(url)
        return ShortenResponse(shortenUrl = shortURL.toString())
    }

    @GetMapping("/redirect/{shortCode}")
    fun redirect(@PathVariable shortCode: String): ResponseEntity<Unit> {
        val fullURI = urlShortenerService.getFullUrlFromShortcode(shortCode)
        return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY.value()).location(fullURI).build()
    }
}