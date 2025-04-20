package origin.interview.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import origin.interview.configuration.ApplicationProperties
import origin.interview.repositories.database.InMemoryRepository
import origin.interview.repositories.database.ShortUrlEntity
import origin.interview.util.CommonHelper
import origin.interview.util.Exceptions.FailedToCreateShortURLException
import origin.interview.util.Exceptions.FullURLNotFoundException
import origin.interview.util.Exceptions.MalformedURLSuppliedException
import java.net.URI
import kotlin.test.Test
import kotlin.test.assertEquals

class UrlShortenerServiceTests {

    companion object {
        val appProperties = mockk<ApplicationProperties>()
    }

    @BeforeEach
    fun beforeAll() {
        every { appProperties.baseUrl } returns "https://short.ly/"
    }

    @Nested
    inner class ShortenURLTests {
        @Test
        fun `shortenURL successful`() {
            val url = "https://test.com"
            val shortCode = "pxY123"
            // mock inMemory repository
            val inMemoryRepository = mockk<InMemoryRepository>()
            every { inMemoryRepository.findByUrl(url) } returns null
            every { inMemoryRepository.save<ShortUrlEntity>(any()) } returns
                    ShortUrlEntity(
                        id = 12345,
                        code = shortCode,
                        url = url
                    )

            // mock the CommonHelper object
            mockkObject(CommonHelper)
            every { CommonHelper.generateShortCode(6) } returns shortCode

            val service = UrlShortenerService(inMemoryRepository, appProperties)
            val response = service.shortenURL(url)
            val expected = URI("https://short.ly/pxY123").toURL()
            Assertions.assertEquals(expected, response)

            unmockkObject(CommonHelper)
        }

        @Test
        fun `shortenURL returns existing if existing full url found in the database`() {
            val url = "https://test.com"
            val shortCode = "pxY123"
            // mock inMemory repository
            val inMemoryRepository = mockk<InMemoryRepository>()
            every { inMemoryRepository.findByUrl(url) } returns
                    ShortUrlEntity(
                        id = 12345,
                        code = shortCode,
                        url = url
                    )

            // Mock the CommonHelper object
            mockkObject(CommonHelper)
            every { CommonHelper.generateShortCode(6) } returns shortCode

            val service = UrlShortenerService(inMemoryRepository, appProperties)
            val response = service.shortenURL(url)
            val expected = URI("https://short.ly/pxY123").toURL()
            Assertions.assertEquals(expected, response)

            unmockkObject(CommonHelper)
        }

        @Test
        fun `shortenURL throws MalformedURLSuppliedException`() {
            val url = "gtps://test.com"
            val inMemoryRepository = mockk<InMemoryRepository>()
            val service = UrlShortenerService(inMemoryRepository, appProperties)
            assertThrows<MalformedURLSuppliedException> {
                service.shortenURL(url)
            }
        }

        @Test
        fun `shortenURL throws FailedToCreateShortURLException`() {
            val url = "https://test.com"
            // mock inMemory repository
            val inMemoryRepository = mockk<InMemoryRepository>()
            every { inMemoryRepository.findByUrl(url) } returns null
            every { inMemoryRepository.save<ShortUrlEntity>(any()) } throws EntityNotFoundException("Not found")
            val service = UrlShortenerService(inMemoryRepository, appProperties)
            assertThrows<FailedToCreateShortURLException> {
                service.shortenURL(url)
            }
        }
    }

    @Nested
    inner class GetFullUrlFromShortcodeTests {
        @Test
        fun `getFullUrlFromShortcode - redirect to full url successful`() {
            val url = "https://test.com"
            val shortCode = "aBc1X3"
            val inMemoryRepository = mockk<InMemoryRepository>()
            every { inMemoryRepository.findByCode(shortCode) } returns
                    ShortUrlEntity(
                        id = 12345,
                        code = shortCode,
                        url = url
                    )
            val service = UrlShortenerService(inMemoryRepository, appProperties)
            val response = service.getFullUrlFromShortcode(shortCode)
            assertEquals(URI(url), response)
        }

        @Test
        fun `getFullUrlFromShortcode throws FullURLNotFoundException`() {
            val shortCode = "aBc1X3"
            val inMemoryRepository = mockk<InMemoryRepository>()
            every { inMemoryRepository.findByCode(shortCode) } returns null
            val service = UrlShortenerService(inMemoryRepository, appProperties)
            assertThrows<FullURLNotFoundException> {
                service.getFullUrlFromShortcode(shortCode)
            }
        }

        @Test
        fun `getFullUrlFromShortcode throws FullURLNotFoundException on database error`() {
            val shortCode = "aBc1X3"
            val inMemoryRepository = mockk<InMemoryRepository>()
            every { inMemoryRepository.findByCode(shortCode) } throws EntityNotFoundException("Not found")
            val service = UrlShortenerService(inMemoryRepository, appProperties)
            assertThrows<FullURLNotFoundException> {
                service.getFullUrlFromShortcode(shortCode)
            }
        }
    }
}