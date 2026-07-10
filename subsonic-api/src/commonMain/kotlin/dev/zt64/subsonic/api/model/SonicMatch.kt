package dev.zt64.subsonic.api.model

import dev.zt64.subsonic.api.SubsonicApi

/**
 * Sonic similarity match
 *
 * @property entry The matched item
 * @property similarity The normalized similarity score (1.0 = same exact song, 0.0 = most different). \
 * For getSonicSimilarTracks, relative to the query song. For [SubsonicApi.findSonicPath], \
 * relative to the starting song. Returns `-1` when similarity is not supported by the server.
 */
public data class SonicMatch private constructor(
    val entry: SubsonicResource,
    val similarity: Float
)