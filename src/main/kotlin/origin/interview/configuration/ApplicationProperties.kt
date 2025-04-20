package origin.interview.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.application")
class ApplicationProperties {
    lateinit var baseUrl: String
}