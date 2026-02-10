package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Resource
 *
 * @param id
 */
@Serializable
public sealed interface Resource {
    public val id: String
    public val coverArt: String?
    public val starred: Instant?
}