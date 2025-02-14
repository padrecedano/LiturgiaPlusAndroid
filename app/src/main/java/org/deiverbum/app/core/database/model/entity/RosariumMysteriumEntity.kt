package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.RosariumMysterium
import org.deiverbum.app.util.Constants

/**
 * Clase de entidad para la tabla `rosarium_mysterium`.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
@Entity(
    tableName = Constants.ROSARIUM_MYSTERIUM,
    foreignKeys = [ForeignKey(
        entity = RosariumSeriesEntity::class,
        parentColumns = arrayOf("seriesID"),
        childColumns = arrayOf("seriesFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class RosariumMysteriumEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mysteriumID")
    var mysteriumID: Int,

    @ColumnInfo(name = "mysterium")
    var mysterium: String,

    @ColumnInfo(name = "seriesFK", index = true)
    var seriesFK: Int,
)

fun RosariumMysteriumEntity.asExternalModel() = RosariumMysterium(
    mysteriumID = mysteriumID,
    mysterium = mysterium,
    seriesFK = seriesFK
)