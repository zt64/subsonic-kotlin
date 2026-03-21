package dev.zt64.subsonic.api.model

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.*
import kotlin.time.Instant

/**
 * Base interface for media resources
 *
 * @property id Unique identifier
 * @property coverArtId ID of the cover art image
 * @property starredAt Timestamp when the resource was starred, if applicable
 */
@Serializable(ResourceSerializer::class)
public sealed interface SubsonicResource {
    public val id: String
    public val coverArtId: String?
    public val starredAt: Instant?
}

internal object ResourceSerializer : JsonContentPolymorphicSerializer<SubsonicResource>(
    baseClass = SubsonicResource::class
) {
    override fun selectDeserializer(
        element: JsonElement
    ): DeserializationStrategy<SubsonicResource> {
        return when (element.jsonObject["mediaType"]!!.jsonPrimitive.content) {
            "song" -> Song.serializer()
            "artist" -> Artist.serializer()
            "album" -> Album.serializer()
            else -> throw SerializationException(
                "Unknown media type: ${element.jsonObject["mediaType"]}"
            )
        }
    }
}