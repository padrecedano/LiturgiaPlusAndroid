package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.model.SaintLife
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`saint_short_life`** de la base de datos, que se ocupa de gestionar la vida breve de los santos para la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.SAINT_SHORT_LIFE,
    foreignKeys = [ForeignKey(
        entity = SaintEntity::class,
        parentColumns = arrayOf("saintID"),
        childColumns = arrayOf("saintFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class SaintShortLifeEntity(
    @PrimaryKey
    @ColumnInfo(name = "saintFK")
    val saintFK: Int,

    @ColumnInfo(name = "shortLife", defaultValue = "")
    val shortLife: String
) {
    val domainModel: SaintLife
        get() {
            val theModel = SaintLife()
            theModel.shortLife = shortLife
            theModel.saintFK = saintFK
            return theModel
        }
}