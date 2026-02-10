package dev.zt64.subsonic.api.model

import dev.zt64.subsonic.api.model.serializer.SubsonicDurationSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Instant

/**
 * TODO
 *
 * @property id
 * @property name
 * @property version
 * @property year
 * @property coverArt
 * @property starred
 * @property duration
 * @property playCount
 * @property genre
 * @property created
 * @property songCount
 * @property played
 * @property userRating
 * @property musicBrainzId
 * @property songs
 */
@Serializable
public data class Album internal constructor(
    override val id: String,
    override val name: String,
    val artist: String,
    val artistId: String,
    val version: String? = null,
    val year: Int,
    override val coverArt: String,
    override val starred: Instant? = null,
    @Serializable(SubsonicDurationSerializer::class)
    override val duration: Duration,
    val playCount: Int = 0,
    val genre: String? = null,
    val created: Instant,
    override val songCount: Int,
    val played: Instant? = null,
    val userRating: Int? = null,
    val musicBrainzId: String? = null,
    @SerialName("song")
    override val songs: List<Song> = emptyList()
) : Resource, TrackCollection

@Serializable
public data class AlbumInfo(
    val musicBrainzId: String? = null,
    val largeImageUrl: String? = null,
    val mediumImageUrl: String? = null,
    val smallImageUrl: String? = null,
    val lastFmUrl: String? = null,
    val notes: String? = null
)

@Serializable
public sealed class AlbumListType(public val value: String) {
    public data object Random : AlbumListType("random")

    public data object Newest : AlbumListType("newest")

    public data object Highest : AlbumListType("highest")

    public data object Frequent : AlbumListType("frequent")

    public data object Recent : AlbumListType("recent")

    public data object Starred : AlbumListType("starred")

    public data object AlphabeticalByName : AlbumListType("alphabeticalByName")

    public data object AlphabeticalByArtist : AlbumListType("alphabeticalByArtist")

    @Serializable
    public data class ByYear(val fromYear: Int, val toYear: Int) : AlbumListType("byYear")

    @Serializable
    public data class ByGenre(val genre: String) : AlbumListType("byGenre")
}