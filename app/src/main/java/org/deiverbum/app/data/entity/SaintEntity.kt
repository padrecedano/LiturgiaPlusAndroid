package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.model.Saint
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 * CREATE TABLE `santo` (
 * `santoId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 * `liturgiaId` INTEGER NOT NULL,
 * `nombre` TEXT NOT NULL,
 * `mes` INTEGER NOT NULL,
 * `dia` INTEGER NOT NULL,
 * `tipoId` INTEGER NOT NULL,
 * `momentoId` INTEGER DEFAULT 0, -- '1:Vigilia 2:Dia',
 * `status` INTEGER DEFAULT 0,   -- '0:HomilyList No, 1:HomilyList Yes',
 * `comunId` INTEGER DEFAULT 0,
 * UNIQUE(`nombre`,`mes`,`dia`)
 * );
 */
@Entity(
    tableName = Constants.SAINT,
    indices = [Index(value = ["theName", "theMonth", "theDay"], unique = true)]
)
class SaintEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saintID")
    var santoId = 0

    @ColumnInfo(name = "theName")
    var theName = ""

    @ColumnInfo(name = "theMonth")
    var theMonth = 0

    @ColumnInfo(name = "theDay")
    var theDay = 0

    @JvmField
    @ColumnInfo(name = "typeFK", defaultValue = "0")
    var tipoId = 0

    @JvmField
    @ColumnInfo(name = "commonFK", defaultValue = "0")
    var comunId = 0
    val domainModel: Saint
        get() {
            val theModel = Saint()
            theModel.setDay(theDay.toString())
            theModel.setMonth(theMonth.toString())
            theModel.theName = theName
            return theModel
        }
}