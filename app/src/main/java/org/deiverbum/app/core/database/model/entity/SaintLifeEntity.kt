package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.alteri.Sanctus
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`saint_life`** de la base de datos, que se ocupa de gestionar la vida de los santos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.SAINT_LIFE,
    foreignKeys = [ForeignKey(
        entity = SaintEntity::class,
        parentColumns = arrayOf("saintID"),
        childColumns = arrayOf("saintFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class SaintLifeEntity(
    @PrimaryKey
    @ColumnInfo(name = "saintFK")
    val saintFK: Int,

    @ColumnInfo(name = "longLife")
    var longLife: String,

    @ColumnInfo(name = "martyrology")
    var martyrology: String,

    @ColumnInfo(name = "theSource")
    var theSource: String
)

fun SaintLifeEntity.asExternalModel(theMonth: Int, theDay: Int, nomen: String) = Sanctus(
    theMonth,
    theDay,
    nomen,
    martyrology,
    longLife
)
