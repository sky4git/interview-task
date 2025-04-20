package origin.interview.endpoints

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import origin.interview.ApplicationController
import origin.interview.model.ShortenResponse
import origin.interview.service.UrlShortenerService
import origin.interview.util.Exceptions.FailedToCreateShortURLException
import java.net.URI
import kotlin.test.assertEquals

// Testing for endpoint: `/shorten`
class ShortenTests {

    @Test
    fun success() {
        val urlShortenerService = mockk<UrlShortenerService>()
        every { urlShortenerService.shortenURL(any()) } returns URI("https://short.ly/aBc12e").toURL()
        val applicationController = ApplicationController(urlShortenerService)
        val response = applicationController.shortenURL("https://test.com")
        assertEquals(
            ShortenResponse(shortenUrl = "https://short.ly/aBc12e"),
            response
        )
    }

    @Test
    fun `throws FailedToCreateShortURLException`() {
        val urlShortenerService = mockk<UrlShortenerService>()
        every { urlShortenerService.shortenURL(any()) } throws FailedToCreateShortURLException("")
        val applicationController = ApplicationController(urlShortenerService)
        assertThrows<FailedToCreateShortURLException> { applicationController.shortenURL("https://test.com") }
    }
}