package origin.interview.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CommonHelperTests {

    @Nested
    inner class GenerateShortCodeTests {

        @Test
        fun `generateShortCode - tests`() {
            var shortcode = CommonHelper.generateShortCode(6)
            Assertions.assertEquals(6, shortcode.length)
            shortcode = CommonHelper.generateShortCode(20)
            Assertions.assertEquals(20, shortcode.length)
        }
    }

    @Nested
    inner class ValidURLTests {

        @Test
        fun `isValidURL - successful`() {
            var url = "http://example.com"
            Assertions.assertEquals(true, CommonHelper.isValidURL(url))
            url = "https://example.com"
            Assertions.assertEquals(true, CommonHelper.isValidURL(url))
            url = "https://example.com/path"
            Assertions.assertEquals(true, CommonHelper.isValidURL(url))
            url = "https://example.com/path?param=value"
            Assertions.assertEquals(true, CommonHelper.isValidURL(url))
        }

        @Test
        fun `isValidURL - unsuccessful`() {
            var url = "htp://example.com"
            Assertions.assertEquals(false, CommonHelper.isValidURL(url))
            url = "ftps://example.com"
            Assertions.assertEquals(false, CommonHelper.isValidURL(url))
            url = "schema://example.com/path"
            Assertions.assertEquals(false, CommonHelper.isValidURL(url))
            url = "://example.com/path?param=value"
            Assertions.assertEquals(false, CommonHelper.isValidURL(url))
        }
    }
}