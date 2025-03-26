package org.deiverbum.app.core.model.alteri

/**
 * Clase que representa los misterios del Rosario.
 *
 * @author A. Cedano
 * @since 2025.1
 * @version 1.0
 * @see [org.deiverbum.app.core.database.model.entity.RosariumMysteriumEntity]
 */

data class RosariumMysterium(
    var mysteriumID: Int,
    var mysterium: String,
    var seriesFK: Int,
)