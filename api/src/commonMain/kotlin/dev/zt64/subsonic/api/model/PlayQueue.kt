package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Play queue
 *
 * @property currentId ID of currently playing song
 * @property position Position in milliseconds of the currently playing song
 * @property username The user this queue belongs to
 * @property modifiedAt Date of modification
 * @property modifiedBy Name of client that modified
 * @property songs List of songs in the queue
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
    val songs: List<Resource>
)

@Serializable
public data class NowPlayingEntry internal constructor(
    val username: String,
    val minutesAgo: Int,
    val playerId: Int,
    val playerName: String? = null
)