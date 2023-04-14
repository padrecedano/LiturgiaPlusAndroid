package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.model.Pater
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 *
 *
 * CREATE TABLE `padre` (
 * `padreId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 * `padre` TEXT NOT NULL,
 * `liturgyName` TEXT NOT NULL,
 * `lugarFK` INTEGER NOT NULL DEFAULT 0,
 * `tipoFK` INTEGER NOT NULL DEFAULT 0,
 * `tituloFK` INTEGER NOT NULL DEFAULT 0,
 * `misionFK` INTEGER NOT NULL DEFAULT 0,
 * `sexoFK` INTEGER NOT NULL DEFAULT 0,
 * `grupoFK` INTEGER NOT NULL DEFAULT 0,
 * UNIQUE  (`padre`,`lugarFK`,`tipoFK`,`tituloFK`,`misionFK`,`sexoFK`,`grupoFK`)
 * );
 */
@Entity(
    tableName = Constants.PATER,
    indices = [Index(
        value = ["pater", "placeFK", "typeFK", "titleFK", "missionFK", "sexFK", "groupFK"],
        unique = true
    )]
)
class PaterEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "paterID")
    var padreId = 0

    @JvmField
    @ColumnInfo(name = "pater")
    var padre = ""

    @JvmField
    @ColumnInfo(name = "liturgyName")
    var liturgyName = ""

    @JvmField
    @ColumnInfo(name = "placeFK", defaultValue = "0")
    var lugarFK = 0

    @JvmField
    @ColumnInfo(name = "typeFK", defaultValue = "0")
    var tipoFK = 0

    @JvmField
    @ColumnInfo(name = "titleFK", defaultValue = "0")
    var tituloFK = 0

    @JvmField
    @ColumnInfo(name = "missionFK", defaultValue = "0")
    var misionFK = 0

    @JvmField
    @ColumnInfo(name = "sexFK", defaultValue = "0")
    var sexoFK = 0

    @JvmField
    @ColumnInfo(name = "groupFK", defaultValue = "0")
    var grupoFK = 0
    fun getLiturgyName(): String {
        return if (liturgyName == "") padre else liturgyName
    }

    val domainModel: Pater
        get() {
            val dm = Pater()
            dm.pater = padre
            return dm
        }
}