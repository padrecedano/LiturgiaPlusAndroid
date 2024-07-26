package org.deiverbum.app.core.model.data

/**
 * Recoge la información para la petición.
 */
data class TopicRequest(
    val date: Int,
    val name: String,
    val resource: String,
    val dynamic: UserDataDynamic,
    val data: List<Universalis>
)
