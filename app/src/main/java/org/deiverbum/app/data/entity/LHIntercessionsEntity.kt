package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_intercessions`** de la base de datos, que se ocupa de gestionar las Preces de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_INTERCESSIONS,
    indices = [Index(value = ["intro", "intercession"], unique = true)]
)
data class LHIntercessionsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "intercessionID")
    var precesId: Int,

    @ColumnInfo(name = "intro")
    var intro: String,

    @ColumnInfo(name = "intercession")
    var preces: String
)