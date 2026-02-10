package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable

@Serializable
public data class TokenInfo internal constructor(val username: String)