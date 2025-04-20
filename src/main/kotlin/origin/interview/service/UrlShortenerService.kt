package origin.interview.service

import org.springframework.stereotype.Service
import origin.interview.configuration.ApplicationProperties
import origin.interview.repositories.database.InMemoryRepository
import origin.interview.repositories.database.ShortUrlEntity
import origin.interview.util.CommonHelper
import origin.interview.util.Exceptions.FailedToCreateShortURLException
import origin.interview.util.Exceptions.FullURLNotFoundException
import origin.interview.util.Exceptions.MalformedURLSuppliedException
import java.net.URI
import java.net.URL
import java.util.logging.Logger

@Service
class UrlShortenerService(
    private val inMemoryRepository: InMemoryRepository,
    private val appProperties: ApplicationProperties
) {

    companion object {
        val log: Logger = Logger.getLogger("UrlShortenerService")
        const val SHORT_CODE_LENGTH = 6
    }

    fun shortenURL(url: String): URL {
        if (!CommonHelper.isValidURL(url)) {
            throw MalformedURLSuppliedException("Invalid URL supplied")
        }
        
        try {
            // check if url already exists
            inMemoryRepository.findByUrl(url)
                ?.let { shortUrlEntity -> return URI("${appProperties.baseUrl}${shortUrlEntity.code}").toURL() }

            val uniqueCode = CommonHelper.generateShortCode(SHORT_CODE_LENGTH)
            val shortUrlEntity = ShortUrlEntity(
                code = uniqueCode,
                url = url,
            )
            // save this record to the database
            inMemoryRepository.save<ShortUrlEntity>(shortUrlEntity)
            return URI("${appProperties.baseUrl}${uniqueCode}").toURL()
        } catch (exception: Exception) {
            log.warning("Short url Generation error: ${exception.message}")
            throw FailedToCreateShortURLException("Short url cannot be generate at this time")
        }
    }

    fun getFullUrlFromShortcode(shortCode: String): URI {
        val shortUrlEntity = try {
            inMemoryRepository.findByCode(shortCode)
        } catch (exception: Exception) {
            log.warning("Database error when trying to find full url by shortCode: $shortCode :: ${exception.message}")
            throw FullURLNotFoundException("Original url not found")
        }

        return if (shortUrlEntity != null) {
            URI(shortUrlEntity.url)
        } else {
            log.warning("Full url cannot be found for the shortCode: $shortCode")
            throw FullURLNotFoundException("Original url not found")
        }
    }
}