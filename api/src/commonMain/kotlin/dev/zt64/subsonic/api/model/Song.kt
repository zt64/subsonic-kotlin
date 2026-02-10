package dev.zt64.subsonic.api.model

import dev.zt64.subsonic.api.model.serializer.SubsonicDurationSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Instant

/**
 * Song
 *
 * @property id
 * @property parentId
 * @property title
 * @property artistName
 * @property artistId
 * @property albumTitle
 * @property albumId
 * @property trackNumber
 * @property discNumber
 * @property year
 * @property genre
 * @property bitRateKbps
 * @property bitDepth
 * @property duration
 * @property sampleRateHz
 * @property audioChannelCount
 * @property userRating
 * @property averageRating
 * @property playCount
 * @property fileSize
 * @property fileExtension
 * @property mimeType
 * @property filePath
 * @property starred
 * @property coverArt
 */
@Serializable
public data class Song internal constructor(
    override val id: String,
    @SerialName("parent")
    val parentId: String? = null,
    val title: String,
    @SerialName("artist")
    val artistName: String,
    val artistId: String,
    @SerialName("album")
    val albumTitle: String,
    val albumId: String,
    @SerialName("track")
    val trackNumber: Int,
    val discNumber: Int,
    val year: Int? = null,
    val genre: String? = null,
    @SerialName("bitRate")
    val bitRateKbps: Int,
    val bitDepth: Int,
    @Serializable(SubsonicDurationSerializer::class)
    val duration: Duration,
    @SerialName("samplingRate")
    val sampleRateHz: Int,
    @SerialName("channelCount")
    val audioChannelCount: Int,
    val userRating: Int? = null,
    val averageRating: Int? = null,
    val playCount: Int = 0,
    @SerialName("size")
    val fileSize: Long,
    @SerialName("suffix")
    val fileExtension: String,
    @SerialName("contentType")
    val mimeType: String,
    @SerialName("path")
    val filePath: String,
    override val starred: Instant? = null,
    override val coverArt: String? = null
) : Resource {
    @Serializable
    public enum class Type {
        MUSIC,
        VIDEO
    }
}

/**
 * Song contributor
 *
 * @property role
 * @property subRole
 */
@Serializable
public data class Contributor(
    val role: String,
    val subRole: String
)

@Serializable
public data class Lyrics(
    val artist: String,
    val title: String,
    val value: String
)

@Serializable
public data class StructuredLyrics internal constructor(
    val lang: String,
    val synced: Boolean,
    val displayArtist: String,
    val displayTitle: String,
    val offset: Int,
    @SerialName("line")
    val lines: List<Line>
) {
    /**
     * Line
     *
     * @property start In milliseconds
     * @property value
     * @constructor Create empty Line
     */
    @Serializable
    public data class Line internal constructor(
        val start: Int,
        val value: String
    )
}