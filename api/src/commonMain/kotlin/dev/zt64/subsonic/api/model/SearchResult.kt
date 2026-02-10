package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Search result
 *
 * @property artists
 * @property albums
 * @property songs
 */
@Serializable
public data class SearchResult internal constructor(
    @SerialName("artist")
    val artists: List<Artist> = emptyList(),
    @SerialName("album")
    val albums: List<Album> = emptyList(),
    @SerialName("song")
    val songs: List<Song> = emptyList()
)