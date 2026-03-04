package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable

/**
 * Music folder
 *
 * @property id Unique folder ID
 * @property name Name of the folder
 */
@Serializable
public data class MusicFolder internal constructor(
    val id: Int,
    val name: String
)