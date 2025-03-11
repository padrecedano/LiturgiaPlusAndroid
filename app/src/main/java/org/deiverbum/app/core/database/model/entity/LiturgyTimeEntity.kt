package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.liturgia.LiturgyTime
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`liturgy_time`** de la base de datos, que se ocupa de gestionar los tiempos litúrgicos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LITURGY_TIME)
data class LiturgyTimeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timeID")
    var timeID: Int,

    @ColumnInfo(name = "timeName")
    var timeName: String,

    @ColumnInfo(name = "liturgyName")
    var liturgyName: String,
)

fun LiturgyTimeEntity.asExternalModel() = LiturgyTime(
    timeID = timeID,
    nomen = timeName,
    externus = liturgyName
)
