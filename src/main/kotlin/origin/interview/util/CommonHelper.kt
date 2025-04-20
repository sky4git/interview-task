package origin.interview.util

object CommonHelper {

    // short code in the short url `http://short.ly/a1B2c3` looks like `a1B2c3`
    fun generateShortCode(length: Int): String {
        // short code seems like made from numbers and alphabets
        val smallAlpha: CharRange = ('a'..'z')
        val capitalAlpha: CharRange = ('A'..'Z')
        val numsRange: CharRange = ('0'..'9')

        val allowedChars = smallAlpha + capitalAlpha + numsRange
        var code = ""
        (1..length).forEach {
            code += allowedChars[allowedChars.indices.random()]
        }
        return code
    }

    fun isValidURL(url: String): Boolean {
        val regex =
            Regex("^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*$")
        return url.matches(regex)
    }
}