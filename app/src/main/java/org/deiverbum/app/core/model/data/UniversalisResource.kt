package org.deiverbum.app.core.model.data

/**
 * Representación de un recurso del tipo **`Universalis`** para la capa de datos externa.
 *
 * @author A. Cedano
 * @since 2025.1
 * @version 1.0
 *
 * @see [Universalis]
 * @see [Liturgy]
 */
data class UniversalisResource(
    val date: Int,
    val id: Int,
    val name: String,
    val title: String,
    val dynamic: UserData,
    val data: Universalis
)

/**
 * Representación de un recurso del tipo **`Universalis`** para la capa de datos externa.
 *
 * @author A. Cedano
 * @since 2025.1
 * @version 1.0
 *
 * @see [Universalis]
 * @see [Liturgy]
 */
data class HomeResource(
    val date: Int,
    val dynamic: UserData,
    val data: Universalis
)