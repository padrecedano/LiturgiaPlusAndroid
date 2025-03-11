package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.liturgia.Kyrie
import org.deiverbum.app.util.Constants

/**
 * Clase de entidad para la tabla `kyrie`.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
@Entity(tableName = Constants.KYRIE)
data class KyrieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "kyrieID")
    var kyrieID: Int,

    @ColumnInfo(name = "kyrie")
    var kyrie: String
)

fun KyrieEntity.asExternalModel() = Kyrie(
    kyrieID = kyrieID,
    kyrie = kyrie
)