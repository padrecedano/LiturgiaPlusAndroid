package org.deiverbum.app.core.model.universalis

/**
 *
 * Clase para manejar sincronización inicial.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class UniversalisSync(
    var lastUpdate: String = "",
    var data: MutableList<Universalis>
)
