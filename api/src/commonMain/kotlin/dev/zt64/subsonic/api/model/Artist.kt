package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Artist
 *
 * @property id
 * @property name
 * @property starred
 * @property coverArt
 * @property albumCount
 * @property userRating
 * @property artistImageUrl
 * @property musicBrainzId
 * @property sortName
 * @property roles
 * @constructor Create empty Artist
 */
@Serializable
public data class Artist internal constructor(
    override val id: String,
    val name: String,
    override val starred: Instant? = null,
    override val coverArt: String? = null,
    val albumCount: Int = 0,
    val userRating: Int = -1,
    val artistImageUrl: String? = null,
    val musicBrainzId: String? = null,
    val sortName: String? = null,
    val roles: List<String> = emptyList(),
    val album: List<Album> = emptyList()
) : Resource

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

@Serializable
public data class Index internal constructor(
    val name: String,
    @SerialName("artist")
    val artists: List<Artist>
)
@Serializable
public data class Indexes internal constructor(
    @SerialName("shortcut")
    val shortcut: List<Artist>,
    @SerialName("child")
    val child: List<Artist>,
    @SerialName("index")
    val index: List<Index>
) {

}

@Serializable
public data class Artists internal constructor(
    val ignoredArticles: String,
    val index: List<Index>
)