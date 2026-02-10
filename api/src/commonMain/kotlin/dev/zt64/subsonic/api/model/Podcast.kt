package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

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

@Serializable
public data class PodcastChannel internal constructor(
    val id: String,
    val url: String,
    val title: String,
    val description: String,
    @SerialName("coverArt")
    val coverArtId: String? = null,
    val originalImageUrl: String? = null,
    val status: PodcastStatus,
    val errorMessage: String? = null,
    @SerialName("episode")
    val episodes: List<PodcastEpisode>
)

@Serializable
public data class PodcastEpisode internal constructor(
    override val id: String,
    override val coverArt: String? = null,
    override val starred: Instant? = null,
    val streamId: String,
    val channelId: String,
    val description: String? = null,
    val status: PodcastStatus,
    val publishDate: Instant
): Resource