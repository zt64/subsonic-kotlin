package dev.zt64.subsonic.api.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

internal class SubsonicResponseSerializer<T : Any>(
    val tSerializer: KSerializer<T>
) : KSerializer<SubsonicResponse<T>> {
    override val descriptor = buildClassSerialDescriptor(
        "SubsonicResponse",
        tSerializer.descriptor
    )

    override fun deserialize(decoder: Decoder): SubsonicResponse<T> {
        require(decoder is JsonDecoder)

        val element = decoder.decodeJsonElement().jsonObject
        val status = element.jsonObject["status"]!!.jsonPrimitive.content
        return if (status == "ok") {
            if (element.entries.size == 6) {
                // There is a data field
                val obj = element.jsonObject.toMutableMap().apply {
                    val dataEntry = entries.last()
                    remove(dataEntry.key)
                    this["data"] = dataEntry.value
                }

                decoder.json.decodeFromJsonElement(
                    SubsonicResponse.Success.serializer(tSerializer),
                    JsonObject(obj)
                )
            } else {
                SubsonicResponse.Empty.serializer().deserialize(decoder)
            }
        } else {
            SubsonicResponse.Error.serializer().deserialize(decoder)
        }
    }

    override fun serialize(encoder: Encoder, value: SubsonicResponse<T>) {
        error("Not needed")
    }
}

@Serializable
public enum class SubsonicStatus {
    @SerialName("ok")
    OK,

    @SerialName("failed")
    FAILED
}

/**
 * Generic Subsonic response
 *
 * @param T
 * @property status Status of the response
 * @property version API version
 * @property type Server name
 * @property serverVersion Server version
 * @property openSubsonic
 */
@Serializable(SubsonicResponseSerializer::class)
public sealed interface SubsonicResponse<out T : Any> {
    public val status: SubsonicStatus
    public val version: String
    public val type: String
    public val serverVersion: String
    public val openSubsonic: Boolean

    /**
     * An empty response with no data, used for endpoints that return no data.
     */
    @Serializable
    public data class Empty internal constructor(
        override val status: SubsonicStatus,
        override val version: String,
        override val type: String,
        override val serverVersion: String,
        override val openSubsonic: Boolean
    ) : SubsonicResponse<Nothing>

    /**
     * Success
     *
     * @param T Type of data
     * @property data The deserialized data as [T]
     */
    @Serializable
    public data class Success<out T : Any> internal constructor(
        override val status: SubsonicStatus,
        override val version: String,
        override val type: String,
        override val serverVersion: String,
        override val openSubsonic: Boolean,
        val data: T
    ) : SubsonicResponse<T>

    /**
     * Subsonic error
     *
     * @property error
     */
    @Serializable
    public data class Error internal constructor(
        override val status: SubsonicStatus,
        override val version: String,
        override val type: String,
        override val serverVersion: String,
        override val openSubsonic: Boolean,
        public val error: Error
    ) : SubsonicResponse<Nothing> {
        @Serializable
        public data class Error internal constructor(
            val code: Int,
            val helpUrl: String? = null,
            val message: String
        )
    }
}