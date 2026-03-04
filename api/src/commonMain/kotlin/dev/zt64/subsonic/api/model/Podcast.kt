package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Podcast status
 */
@Serializable
public enum class PodcastStatus {
    @SerialName("new")
    NEW,

    @SerialName("downloading")
    DOWNLOADING,

    @SerialName("completed")
    COMPLETED,

    @SerialName("error")
    ERROR,

    @SerialName("skipped")
    SKIPPED
}

/**
 * Podcast channel information
 *
 * @property id Unique channel identifier
 * @property title Channel title
 * @property url Channel RSS feed URL
 * @property description Channel description
 * @property coverArtId Cover art ID
 * @property originalImageUrl Original cover art URL from feed
 * @property status Channel status
 * @property errorMessage Error message if status is ERROR
 * @property episodes List of podcast episodes
 */
@Serializable
public data class PodcastChannel internal constructor(
    val id: String,
    val title: String,
    val url: String,
    val description: String,
    @SerialName("coverArt")
    val coverArtId: String? = null,
    val originalImageUrl: String? = null,
    val status: PodcastStatus,
    val errorMessage: String? = null,
    @SerialName("episode")
    val episodes: List<PodcastEpisode>
)

/**
 * Podcast episode information
 *
 * @property id Unique episode identifier
 * @property streamId Stream ID for playback
 * @property channelId Parent channel identifier
 * @property description Episode description or show notes
 * @property coverArtId Cover art ID
 * @property status Episode download status
 * @property publishDate Episode publication date
 * @property starredAt Timestamp when starred, or null if not starred
 */
@Serializable
public data class PodcastEpisode internal constructor(
    override val id: String,
    val streamId: String,
    val channelId: String,
    val description: String? = null,
    @SerialName("coverArt")
    override val coverArtId: String? = null,
    val status: PodcastStatus,
    val publishDate: Instant,
    @SerialName("starred")
    override val starredAt: Instant? = null
) : Resource