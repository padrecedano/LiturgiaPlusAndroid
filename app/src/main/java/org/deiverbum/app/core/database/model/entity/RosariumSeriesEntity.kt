package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.alteri.RosariumSeries
import org.deiverbum.app.util.Constants

/**
 * Clase de entidad para la tabla `rosarium_series`.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
@Entity(tableName = Constants.ROSARIUM_SERIES)
data class RosariumSeriesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "seriesID")
    var seriesID: Int,

    @ColumnInfo(name = "series")
    var series: String
)

fun RosariumSeriesEntity.asExternalModel() = RosariumSeries(
    seriesID = seriesID,
    series = series
)

//seriesID = seriesID,
//series = series
