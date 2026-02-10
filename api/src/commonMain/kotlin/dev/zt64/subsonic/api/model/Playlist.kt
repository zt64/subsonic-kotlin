package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Instant

/**
 * Track collection
 */
@Serializable
public sealed interface TrackCollection {
    public val id: String
    public val name: String
    public val coverArt: String?
    public val duration: Duration
    public val songCount: Int
    public val songs: List<Song>
}

@Serializable
public data class Playlist internal constructor(
    override val id: String,
    override val name: String,
    override val coverArt: String,
    val comment: String? = null,
    val owner: String,
    val public: Boolean? = null,
    override val duration: Duration,
    val created: Instant,
    @SerialName("changed")
    val modified: Instant,
    val allowedUsers: List<String> = emptyList(),
    override val songCount: Int,
    val readOnly: Boolean? = null,
    val validUntil: Instant? = null,
    @SerialName("entry")
    override val songs: List<Song> = emptyList(),
    override val starred: Instant?
) : Resource, TrackCollection