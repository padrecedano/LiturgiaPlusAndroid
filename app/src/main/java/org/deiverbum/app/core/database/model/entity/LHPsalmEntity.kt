package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_psalm`** de la base de datos, que se ocupa de los salmos de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_PSALM)
data class LHPsalmEntity(
    @PrimaryKey
    @ColumnInfo(name = "psalmID")
    val salmoId: Int,

    @ColumnInfo(name = "psalm")
    val salmo: String,

    @ColumnInfo(name = "readingID")
    val pericopaId: Int,

    @ColumnInfo(name = "quote")
    val salmoRef: String?
)