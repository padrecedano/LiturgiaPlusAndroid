package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.breviarium.LHResponsoriumBrevis
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_responsory_short`** de la base de datos, que se ocupa de gestionar los responsorios de las Lecturas Breves de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_RESPONSORY_SHORT,
    indices = [Index(value = ["text"], unique = true)]
)
data class LHResponsoryShortEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "responsoryID")
    val responsorioId: Int,

    @ColumnInfo(name = "text")
    val texto: String,

    @ColumnInfo(name = "type")
    var tipo: Int
)

fun LHResponsoryShortEntity.asExternalModel() = LHResponsoriumBrevis(
    texto,
    tipo
)
