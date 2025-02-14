package org.deiverbum.app.core.model.data

/**
 * Clase que representa las cuatro series de misterios del Rosario.
 *
 * @author A. Cedano
 * @since 2025.1
 * @version 1.0
 * @see [org.deiverbum.app.core.database.model.entity.RosariumSeriesEntity]
 */

data class RosariumSeries(
    var seriesID: Int,
    var series: String
)