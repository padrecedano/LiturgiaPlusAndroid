package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`rosarium_series_dies`** de la base de datos,
 * que se ocupa de gestionar la relación de los misterios del Rosario por día.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
@Entity(
    tableName = Constants.ROSARIUM_SERIES_DIES,
    primaryKeys = ["seriesFK", "dies"],
    foreignKeys =
    [
        ForeignKey(
            entity = RosariumSeriesEntity::class,
            parentColumns = arrayOf("seriesID"),
            childColumns = arrayOf("seriesFK"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        )]
)
data class RosariumSeriesDiesEntity(
    @ColumnInfo(name = "seriesFK", index = true)
    var seriesFK: Int,

    @ColumnInfo(name = "dies", index = true)
    var dies: Int
)
