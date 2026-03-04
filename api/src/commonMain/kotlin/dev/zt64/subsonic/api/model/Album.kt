package dev.zt64.subsonic.api.model

import dev.zt64.subsonic.api.model.serializer.SubsonicDurationSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Instant

/**
 * Album information
 *
 * @property id Unique album identifier
 * @property name Album name
 * @property artistName Artist name
 * @property artistId Artist identifier
 * @property year Release year
 * @property coverArtId Cover art ID
 * @property genre Album genre
 * @property songCount Number of songs in the album
 * @property duration Total album duration
 * @property createdAt Timestamp when added to library
 * @property starredAt Timestamp when starred, or null if not starred
 * @property lastPlayedAt Timestamp when last played
 * @property playCount Number of times played
 * @property userRating User rating (1-5)
 * @property version Album version or edition
 * @property musicBrainzId MusicBrainz identifier
 * @property songs List of songs in the album
 */
@Serializable
public data class Album internal constructor(
    override val id: String,
    override val name: String,
    @SerialName("artist")
    val artistName: String,
    val artistId: String,
    val year: Int,
    @SerialName("coverArt")
    override val coverArtId: String,
    val genre: String? = null,
    override val songCount: Int,
    @Serializable(SubsonicDurationSerializer::class)
    override val duration: Duration,
    @SerialName("created")
    val createdAt: Instant,
    @SerialName("starred")
    override val starredAt: Instant? = null,
    @SerialName("played")
    val lastPlayedAt: Instant? = null,
    val playCount: Int = 0,
    val userRating: Int? = null,
    val version: String? = null,
    val musicBrainzId: String? = null,
    @SerialName("song")
    override val songs: List<Song> = emptyList()
) : Resource, SongCollection

/**
 * Detailed album information from Last.fm
 *
 * @property musicBrainzId MusicBrainz identifier
 * @property largeImageUrl Large album image URL
 * @property mediumImageUrl Medium album image URL
 * @property smallImageUrl Small album image URL
 * @property lastFmUrl Last.fm album page URL
 * @property notes Album notes or description
 */
@Serializable
public data class AlbumInfo(
    val musicBrainzId: String? = null,
    val largeImageUrl: String? = null,
    val mediumImageUrl: String? = null,
    val smallImageUrl: String? = null,
    val lastFmUrl: String? = null,
    val notes: String? = null
)

/**
 * Album list filtering type
 *
 * @property value The API parameter value
 */
@Serializable
public sealed class AlbumListType(public val value: String) {
    /** Random albums */
    public data object Random : AlbumListType("random")

    /** Newest albums */
    public data object Newest : AlbumListType("newest")

    /** Highest rated albums */
    public data object Highest : AlbumListType("highest")

    /** Most frequently played albums */
    public data object Frequent : AlbumListType("frequent")

    /** Recently played albums */
    public data object Recent : AlbumListType("recent")

    /** Starred albums */
    public data object Starred : AlbumListType("starred")

    /** Alphabetically sorted by album name */
    public data object AlphabeticalByName : AlbumListType("alphabeticalByName")

    /** Alphabetically sorted by artist name */
    public data object AlphabeticalByArtist : AlbumListType("alphabeticalByArtist")

    /**
     * Albums released within a year range
     *
     * @property fromYear Start year (inclusive)
     * @property toYear End year (inclusive)
     */
    @Serializable
    public data class ByYear(val fromYear: Int, val toYear: Int) : AlbumListType("byYear")

    /**
     * Albums by genre
     *
     * @property genre Genre name
     */
    @Serializable
    public data class ByGenre(val genre: String) : AlbumListType("byGenre")
}