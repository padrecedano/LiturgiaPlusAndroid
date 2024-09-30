package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.LHHymn
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_hymn`** de la base de datos, que se ocupa de los Himnos de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_HYMN, indices = [Index(value = ["hymn"], unique = true)])
data class LHHymnEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hymnID")
    var hymnID: Int,

    @ColumnInfo(name = "hymn")
    var hymn: String
)

fun LHHymnEntity.asExternalModel() = LHHymn(
    //hymnID = hymnID,
    hymnus = hymn
)

