package origin.interview

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import origin.interview.model.ShortUrlInfo
import origin.interview.service.UrlShortenerService
import java.net.URI
import kotlin.test.assertEquals

@SpringBootTest
class UrlShortenerApplicationTests(
    @Autowired val urlShortenerService: UrlShortenerService
) {

    val applicationController = ApplicationController(urlShortenerService = urlShortenerService)

    @Test
    fun `should shorten URL successfully`() {
        val originalUrl = "http://example.com"
        val response = applicationController.shortenURL(originalUrl)

        assert(response.shortUrl.toString().startsWith("http://short.ly/")) {
            "Short URL should start with short.ly/"
        }
    }

    @Test
    fun `should redirect url based on shortCode`() {
        val originalUrl = "http://bing.com"
        val shortUrl = urlShortenerService.shortenURL(originalUrl)
        val generatedShortCode = shortUrl.path.substringAfter("/")
        val response = applicationController.redirect(generatedShortCode)
        assertEquals(ResponseEntity.status(302).location(URI(originalUrl)).build(), response)
    }

    @Test
    fun `should return the correct short url info by the shortCode supplied`() {
        val originalUrl = "http://test.com"
        val shortUrl = urlShortenerService.shortenURL(originalUrl)
        val generatedShortCode = shortUrl.path.substringAfter("/")
        // increase the clicks count by invoking `redirect/{shortCode}` 2 times
        applicationController.redirect(generatedShortCode)
        applicationController.redirect(generatedShortCode)
        // get the shorturl info
        val response = applicationController.info(generatedShortCode)
        assertEquals(
            ShortUrlInfo(
                shortUrl = "http://short.ly/$generatedShortCode",
                fullUrl = originalUrl,
                clicks = 2,
            ), response
        )
    }
}
