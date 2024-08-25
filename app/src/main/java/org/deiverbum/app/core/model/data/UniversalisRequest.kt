package org.deiverbum.app.core.model.data

/**
 * Recoge la información para la petición.
 */
data class UniversalisRequest(
    val date: Int,
    val id: Int,
    val name: String,
    val resource: String,
    val dynamic: UserDataDynamic,
    val data: List<Universalis>
)
