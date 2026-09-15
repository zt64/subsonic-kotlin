package dev.zt64.subsonic.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Client information, generally used for providing information about supported codecs and containers for the server.
 * `getTranscodeDecision` requires this information for yielding direct play and transcode profiles to the client.
 * @property codecProfiles Codec profiles
 * @property directPlayProfiles Direct play profiles
 * @property maxAudioBitrate Maximum audio bitrate
 * @property name Client name
 * @property platform Platform name
 * @property transcodingProfiles Transcoding profiles
 */
@Serializable
public data class ClientInfo(
    val codecProfiles: List<CodecProfile>,
    val directPlayProfiles: List<DirectPlayProfile>,
    val maxAudioBitrate: Int = 0,
    val maxTranscodingAudioBitrate: Int = 0,
    val name: String,
    val platform: String,
    val transcodingProfiles: List<TranscodingProfile>
) {
    @Serializable
    public enum class CodecType {
        AudioCodec
    }

    @Serializable
    public enum class LimitationComparison {
        Equals,
        NotEquals,
        LessThanEqual,
        GreaterThanEqual
    }

    @Serializable
    public enum class LimitationName {
        @SerialName("audioChannels")
        AudioChannels,
        @SerialName("audioBitrate")
        AudioBitrate,
        @SerialName("audioProfile")
        AudioProfile,
        @SerialName("audioSamplerate")
        AudioSampleRate,
        @SerialName("audioBitdepth")
        AudioBitDepth,
    }

    @Serializable
    public enum class Protocol {
        @SerialName("http") HTTP,
        @SerialName("https") HTTPS,
        @SerialName("hls") HLS
    }

    /**
     * Codec profile
     * @property limitations List of limitations
     * @property name Profile name
     * @property type Codec type
     */
    @Serializable
    public data class CodecProfile(
        val name: String,
        val type: CodecType,
        val limitations: List<Limitation> = emptyList()
    ) {
        /**
         * Limitation
         * @property comparison Comparison
         * @property name Limitation name
         * @property required Whether is it required
         * @property values Values
         */
        @Serializable
        public data class Limitation(
            val comparison: LimitationComparison,
            val name: LimitationName,
            val required: Boolean = true,
            val values: List<String>
        )
    }
    /**
     * Transcoding profile
     * @property audioCodec Audio codec
     * @property container Container for the codec
     * @property maxAudioChannels Amount of maximum audio channels
     * @property protocol Supported transport protocol (HTTP, HLS...)
     */
    @Serializable
    public data class TranscodingProfile(
        val audioCodec: String,
        val container: String,
        val maxAudioChannels: Int,
        val protocol: Protocol
    )

    /**
     * Direct play profile
     * @property audioCodecs List of supported audio codecs
     * @property containers List of supported containers
     * @property maxAudioChannels Maximum amount of audio channels
     * @property protocols Supported transport protocols (HTTP, HLS...)
     */
    @Serializable
    public data class DirectPlayProfile(
        val audioCodecs: List<String>,
        val containers: List<String> = emptyList(),
        val maxAudioChannels: Int? = null,
        val protocols: List<Protocol>
    )
}