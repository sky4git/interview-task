package origin.interview.util

object Exceptions {
    /**
     * Thrown when for any reason we failed to create short url
     */
    class FailedToCreateShortURLException(message: String) : RuntimeException(message)

    /**
     * Thrown when supplied URL is not valid URL
     */
    class MalformedURLSuppliedException(message: String) : RuntimeException(message)

    /**
     * Thrown when for any reason we cannot find the full url for the supplied shortCode
     */
    class FullURLNotFoundException(message: String) : RuntimeException(message)
}