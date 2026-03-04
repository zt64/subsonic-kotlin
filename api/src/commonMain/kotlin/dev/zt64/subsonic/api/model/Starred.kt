package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable

/**
 * Starred information
 *
 * @property artists List of starred artists
 * @property albums List of starred albums
 * @property songs List of starred songs
 */
@Serializable
public data class Starred internal constructor(
    val artists: List<Artist> = emptyList(),
    val albums: List<Album> = emptyList(),
    val songs: List<Song> = emptyList()
)