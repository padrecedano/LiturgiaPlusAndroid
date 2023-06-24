package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Clase de entidad para la tabla **`virgin_antiphon`**.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
@Entity(tableName = Constants.VIRGIN_ANTIPHON)
class VirginAntiphonEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "antiphonID")
    var antiphonID = 0

    @JvmField
    @ColumnInfo(name = "antiphon")
    var antiphon = ""
}