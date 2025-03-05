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
    val title: String,
    val meta: String,
    val metaData: MetaData,

    val dynamic: UserData,
    val data: Universalis
)

/**
 * Metadatos de los recursos para la pantalla.
 *
 * @author A. Cedano
 * @since 2025.1
 * @version 1.0
 *
 */
data class MetaData(
    val liturgia: String,
    val tempus: String,
    val nomen: String,
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
    val data: Universalis,
    val count: Int = 0,
    val dynamic: UserData,
)