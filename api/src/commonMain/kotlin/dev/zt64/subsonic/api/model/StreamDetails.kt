package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable

@Serializable
public data class StreamDetails internal constructor(
    val audioBitdepth: Int,
    val audioBitrate: Int,
    val audioChannels: Int,
    val audioProfile: String,
    val audioSamplerate: Int,
    val codec: String,
    val container: String,
    val protocol: String
)