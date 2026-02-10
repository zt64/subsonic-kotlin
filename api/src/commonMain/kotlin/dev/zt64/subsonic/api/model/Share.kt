package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Share
 *
 * @property id The share ID
 * @property url The share URL
 * @property description A description
 * @property username
 * @property created
 * @property expires
 * @property lastVisited
 * @property visitCount
 * @property items
 */
@Serializable
public data class Share internal constructor(
    val id: String,
    val url: String,
    val description: String,
    val username: String,
    val created: Instant,
    val expires: Instant,
    val lastVisited: Instant? = null,
    val visitCount: Int,
    @SerialName("entry")
    val items: List<Resource> = emptyList()
)