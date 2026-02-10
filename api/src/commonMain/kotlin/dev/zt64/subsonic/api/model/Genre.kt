package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param name The name of this genre
 */
@Serializable
public data class Genre internal constructor(
    @SerialName("value")
    val name: String,
    val albumCount: Int,
    val songCount: Int
)