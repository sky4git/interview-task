package origin.interview.endpoints

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import origin.interview.ApplicationController
import origin.interview.model.ShortUrlInfo
import origin.interview.service.UrlShortenerService
import origin.interview.service.model.ShortUrlMetadata
import origin.interview.util.Exceptions.FullURLNotFoundException
import kotlin.test.assertEquals

// Testing for endpoint `/info/{shortCode}`
class InfoTests {
    @Test
    fun success() {
        val fullUrl = "https://example.com"
        val urlShortenerService = mockk<UrlShortenerService>()
        every { urlShortenerService.getInfoByShortcode(any()) } returns ShortUrlMetadata(
            shortUrl = "http://short.ly/testCode",
            originalUrl = fullUrl,
            clicks = 101,
        )
        val applicationController = ApplicationController(urlShortenerService)
        val response = applicationController.info("testCode")
        assertEquals(
            ShortUrlInfo(
                shortUrl = "http://short.ly/testCode",
                fullUrl = fullUrl,
                clicks = 101,
            ),
            response
        )
    }

    @Test
    fun `throws FullURLNotFoundException`() {
        val urlShorterService = mockk<UrlShortenerService>()
        every { urlShorterService.getInfoByShortcode(any()) } throws FullURLNotFoundException("")
        val applicationController = ApplicationController(urlShorterService)
        assertThrows<FullURLNotFoundException> { applicationController.info("testCode") }
    }
}