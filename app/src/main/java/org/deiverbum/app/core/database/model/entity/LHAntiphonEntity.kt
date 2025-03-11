package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.breviarium.LHAntiphon
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_antiphon`** de la base de datos, que se ocupa de las antífonas de los salmos para la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_ANTIPHON, indices = [Index(value = ["antiphon"], unique = true)])
data class LHAntiphonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "antiphonID")
    var antiphonID: Int = 0,

    @ColumnInfo(name = "antiphon")
    var antiphon: String = ""
)

fun LHAntiphonEntity.asExternalModel() = LHAntiphon(
    antiphonID = antiphonID,
    antiphon = antiphon
)