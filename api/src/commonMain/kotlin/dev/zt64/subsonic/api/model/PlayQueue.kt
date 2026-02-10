package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Play queue
 *
 * @property currentId
 * @property position
 * @property username
 * @property modifiedAt
 * @property modifiedBy
 * @property items
 */
@Serializable
public data class PlayQueue internal constructor(
    val currentId: String,
    val position: Long = 0,
    val username: String,
    @SerialName("changed")
    val modifiedAt: Instant,
    @SerialName("changedBy")
    val modifiedBy: String,
    @SerialName("entry")
    val items: List<Resource>
)

@Serializable
public data class NowPlayingEntry internal constructor(
    val username: String,
    val minutesAgo: Int,
    val playerId: Int,
    val playerName: String? = null,
)
