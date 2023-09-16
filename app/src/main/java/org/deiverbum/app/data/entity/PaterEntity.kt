package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.model.Pater
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`pater`** de la base de datos, que se ocupa de gestionar los Padres de la Iglesia.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.PATER,
    indices = [Index(
        value = ["pater", "placeFK", "typeFK", "titleFK", "missionFK", "sexFK", "groupFK"],
        unique = true
    )]
)
data class PaterEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "paterID")
    val padreId: Int,

    @ColumnInfo(name = "pater")
    val padre: String,

    @ColumnInfo(name = "liturgyName")
    val liturgyName: String,

    @ColumnInfo(name = "placeFK", defaultValue = "0")
    val lugarFK: Int,

    @ColumnInfo(name = "typeFK", defaultValue = "0")
    val tipoFK: Int,

    @ColumnInfo(name = "titleFK", defaultValue = "0")
    val tituloFK: Int,

    @ColumnInfo(name = "missionFK", defaultValue = "0")
    var misionFK: Int,

    @ColumnInfo(name = "sexFK", defaultValue = "0")
    var sexoFK: Int,

    @ColumnInfo(name = "groupFK", defaultValue = "0")
    var grupoFK: Int
) {


    val domainModel: Pater
        get() {
            val dm = Pater()
            dm.pater = padre
            return dm
        }
}