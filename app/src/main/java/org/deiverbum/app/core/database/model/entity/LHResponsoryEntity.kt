package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.breviarium.LHResponsorium
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_responsory`** de la base de datos, que se ocupa de gestionar los responsorios de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_RESPONSORY)
data class LHResponsoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "responsoryID")
    val responsorioId: Int,

    @ColumnInfo(name = "text")
    val texto: String,

    @ColumnInfo(name = "source")
    val fuente: String,

    @ColumnInfo(name = "type")
    val tipo: Int
)

fun LHResponsoryEntity.asExternalModel() = LHResponsorium(
    texto,
    tipo,
    fuente
)