package org.deiverbum.app.core.network.model

import org.deiverbum.app.core.model.universalis.Universalis

/**
 * Network representation of [Topic]
 */
//@Serializable
data class NetworkUniversalis(
    val lastUpdate: String,
    val data: List<Universalis>
)
