package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * A chat message
 *
 * @property username The user who sent the message
 * @property time Time message was sent
 * @property message The message content
 */
@Serializable
public data class ChatMessage internal constructor(
    val username: String,
    val time: Instant,
    val message: String
)