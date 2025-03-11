package org.deiverbum.app.core.model.data.alteri

/**
 * Clase que representa los misterios del Rosario.
 *
 * @author A. Cedano
 * @since 2025.1
 * @version 1.0
 * @see [org.deiverbum.app.core.database.model.entity.RosariumMysteriumEntity]
 */

data class RosariumMysteriumOrdo(
    var mysterium: RosariumMysterium,
    var ordo: Int
) {
    companion object {
        var ORDINAL: List<String> = listOf(
            "Primer Misterio",
            "Segundo Misterio",
            "Tercer Misterio",
            "Cuarto Misterio",
            "Quinto Misterio"
        )
    }
}