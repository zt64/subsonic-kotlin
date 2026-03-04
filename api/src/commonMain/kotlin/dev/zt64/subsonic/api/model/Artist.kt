package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Artist information
 *
 * @property id Unique artist identifier
 * @property name Artist name
 * @property albumCount Number of albums by this artist
 * @property coverArtId Cover art ID
 * @property artistImageUrl Direct URL to artist image
 * @property starredAt Timestamp when starred, or null if not starred
 * @property userRating User rating (1-5), or -1 if not rated
 * @property sortName Alternate name for sorting
 * @property musicBrainzId MusicBrainz identifier
 * @property roles List of artist roles (e.g., composer, performer)
 * @property album List of albums by this artist
 */
@Serializable
public data class Artist internal constructor(
    override val id: String,
    val name: String,
    val albumCount: Int = 0,
    @SerialName("coverArt")
    override val coverArtId: String? = null,
    val artistImageUrl: String? = null,
    @SerialName("starred")
    override val starredAt: Instant? = null,
    val userRating: Int = -1,
    val sortName: String? = null,
    val musicBrainzId: String? = null,
    val roles: List<String> = emptyList(),
    val album: List<Album> = emptyList()
) : Resource

/**
 * Detailed artist information from Last.fm
 *
 * @property musicBrainzId MusicBrainz identifier
 * @property biography Artist biography text
 * @property smallImageUrl Small artist image URL
 * @property mediumImageUrl Medium artist image URL
 * @property largeImageUrl Large artist image URL
 * @property lastFmUrl Last.fm profile URL
 * @property similarArtists List of similar artists
 */
@Serializable
public data class ArtistInfo internal constructor(
    val musicBrainzId: String,
    val biography: String,
    val smallImageUrl: String,
    val mediumImageUrl: String,
    val largeImageUrl: String,
    val lastFmUrl: String? = null,
    val similarArtists: List<Artist> = emptyList()
)

/**
 * Artist index grouping (e.g., by first letter)
 *
 * @property name Index name (e.g., "A", "B", "Rock")
 * @property artists List of artists in this index
 */
@Serializable
public data class Index internal constructor(
    val name: String,
    @SerialName("artist")
    val artists: List<Artist>
)

/**
 * Collection of indexed artists with shortcuts and children
 *
 * @property shortcut Shortcut artists (frequently accessed)
 * @property child Child entries
 * @property index Artist indexes grouped by name
 */
@Serializable
public data class Indexes internal constructor(
    @SerialName("shortcut")
    val shortcut: List<Artist>,
    @SerialName("child")
    val child: List<Artist>,
    @SerialName("index")
    val index: List<Index>
)

/**
 * ID3-organized artist collection
 *
 * @property ignoredArticles Articles to ignore when sorting (e.g., "The", "A")
 * @property index Artist indexes
 */
@Serializable
public data class Artists internal constructor(
    val ignoredArticles: String,
    val index: List<Index>
)