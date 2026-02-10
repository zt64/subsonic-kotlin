package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * License
 *
 * @property valid
 * @property email
 * @property licenseExpires
 * @property trialExpires
 * @constructor Create empty License
 */
@Serializable
public data class License internal constructor(
    val valid: Boolean,
    val email: String? = null,
    val licenseExpires: Instant? = null,
    val trialExpires: Instant? = null
)