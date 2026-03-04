package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Base interface for media resources
 *
 * @property id Unique identifier
 * @property coverArtId ID of the cover art image
 * @property starredAt Timestamp when the resource was starred, if applicable
 */
@Serializable
public sealed interface Resource {
    public val id: String
    public val coverArtId: String?
    public val starredAt: Instant?
}