package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable

@Serializable
public data class Starred internal constructor(
    val artists: List<Artist>,
    val albums: List<Album>,
    val songs: List<Song>
)
