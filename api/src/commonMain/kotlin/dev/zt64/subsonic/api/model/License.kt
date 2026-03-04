package dev.zt64.subsonic.api.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * License information
 *
 * @property valid Whether the license is valid
 * @property email The user email
 * @property licenseExpires End of license date
 * @property trialExpires End of trial date
 */
@Serializable
public data class License internal constructor(
    val valid: Boolean,
    val email: String? = null,
    val licenseExpires: Instant? = null,
    val trialExpires: Instant? = null
)