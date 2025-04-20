package origin.interview.endpoints

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.ResponseEntity
import origin.interview.ApplicationController
import origin.interview.service.UrlShortenerService
import origin.interview.util.Exceptions.FullURLNotFoundException
import java.net.URI
import kotlin.test.assertEquals

// Testing for endpoint `/redirect/{shortCode}`
class RedirectTests {

    @Test
    fun success() {
        val fullUrl = "https://example.com"
        val urlShortenerService = mockk<UrlShortenerService>()
        every { urlShortenerService.getFullUrlFromShortcode(any()) } returns URI(fullUrl)
        val applicationController = ApplicationController(urlShortenerService)
        val response = applicationController.redirect("testCode")
        assertEquals(
            ResponseEntity.status(302).location(URI(fullUrl)).build(),
            response
        )
    }

    @Test
    fun `throws FullURLNotFoundException`() {
        val urlShorterService = mockk<UrlShortenerService>()
        every { urlShorterService.getFullUrlFromShortcode(any()) } throws FullURLNotFoundException("")
        val applicationController = ApplicationController(urlShorterService)
        assertThrows<FullURLNotFoundException> { applicationController.redirect("testCode") }
    }
}