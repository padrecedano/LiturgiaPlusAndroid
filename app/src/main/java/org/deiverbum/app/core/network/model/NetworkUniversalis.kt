package org.deiverbum.app.core.network.model

import kotlinx.serialization.Serializable

/**
 * Network representation of [Topic]
 */
@Serializable
data class NetworkUniversalis(
    val id: String,
    val name: String = "",
    val shortDescription: String = "",
    val longDescription: String = "",
    val url: String = "",
    val imageUrl: String = "",
    val followed: Boolean = false,
)
