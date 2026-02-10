package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Bookmark
 *
 * @param T
 * @property changedAt
 * @property createdAt Creation date
 * @property comment
 * @property position Bookmark position in milliseconds
 * @property username
 * @property entry
 */
@Serializable
public data class Bookmark<T : Resource> internal constructor(
    @SerialName("changed")
    val changedAt: Instant,
    @SerialName("created")
    val createdAt: Instant,
    val comment: String,
    val position: Int,
    val username: String,
    val entry: T
)