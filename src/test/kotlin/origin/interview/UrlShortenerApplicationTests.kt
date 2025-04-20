package origin.interview

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import origin.interview.service.UrlShortenerService
import java.net.URI
import kotlin.test.assertEquals

@SpringBootTest
class UrlShortenerApplicationTests(
    @Autowired val urlShortenerService: UrlShortenerService
) {

    @Test
    fun `should shorten URL successfully`() {
        val originalUrl = "http://example.com"
        val shortUrl = urlShortenerService.shortenURL(originalUrl)

        assert(shortUrl.toString().startsWith("https://short.ly/")) {
            "Short URL should start with short.ly/"
        }
    }

    @Test
    fun `should redirect url based on shortCode`() {
        val originalUrl = "http://example.com"
        val shortUrl = urlShortenerService.shortenURL(originalUrl)
        val generatedShortCode = shortUrl.path.substringAfter("/")
        val response = urlShortenerService.getFullUrlFromShortcode(generatedShortCode)
        assertEquals(URI(originalUrl), response)
    }

}
