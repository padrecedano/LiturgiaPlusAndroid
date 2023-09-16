package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_epigraph`** de la base de datos, que se ocupa de los epígrafes de los salmos en la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_EPIGRAPH, indices = [Index(value = ["epigraph"], unique = true)])
data class EpigraphEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "epigraphID")
    var epigrafeId: Int,

    @ColumnInfo(name = "epigraph")
    var epigrafe: String
)
