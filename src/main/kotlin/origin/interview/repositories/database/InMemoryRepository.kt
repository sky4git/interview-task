package origin.interview.repositories.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InMemoryRepository : JpaRepository<ShortUrlEntity, Long> {
    // return the record by the short url unique code
    fun findByCode(code: String): ShortUrlEntity?

    // return the record by the url
    fun findByUrl(url: String): ShortUrlEntity?
}