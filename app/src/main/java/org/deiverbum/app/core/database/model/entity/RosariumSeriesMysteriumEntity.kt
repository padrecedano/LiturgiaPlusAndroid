package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`rosarium_series_mysterium`** de la base de datos,
 * que se ocupa de gestionar la relación de los misterios del Rosario por día y orden.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 * @see [RosariumSeriesDiesEntity]
 */
@Entity(
    tableName = Constants.ROSARIUM_SERIES_MYSTERIUM,
    primaryKeys = ["seriesFK", "mysteriumFK", "ordo"],
    foreignKeys =
    [
        ForeignKey(
            entity = RosariumSeriesEntity::class,
            parentColumns = arrayOf("seriesID"),
            childColumns = arrayOf("seriesFK"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        ), ForeignKey(
        entity = RosariumMysteriumEntity::class,
        parentColumns = arrayOf("mysteriumID"),
        childColumns = arrayOf("mysteriumFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class RosariumSeriesMysteriumEntity(
    @ColumnInfo(name = "seriesFK", index = true)
    var seriesFK: Int,

    @ColumnInfo(name = "mysteriumFK", index = true)
    var mysteriumFK: Int,

    @ColumnInfo(name = "ordo", index = true)
    var ordo: Int
)
