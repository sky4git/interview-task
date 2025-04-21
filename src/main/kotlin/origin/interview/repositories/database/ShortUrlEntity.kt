package origin.interview.repositories.database

import jakarta.persistence.*

@Entity
data class ShortUrlEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null,

    @Column(unique = true, nullable = false)
    val url: String,

    @Column(unique = true, nullable = false)
    val code: String,

    @Column(nullable = false)
    var clicks: Int = 0,
)
