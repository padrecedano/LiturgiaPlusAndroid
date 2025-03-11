package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.biblia.BibleBook
import org.deiverbum.app.core.model.data.breviarium.LHLectioBrevis
import org.deiverbum.app.core.model.data.breviarium.LHResponsoriumBrevis
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_reading_short`** de la base de datos, que se ocupa de gestionar las lecturas breves de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_READING_SHORT, indices = [Index(value = ["text"], unique = true)])
data class LHReadingShortEntity(
    @PrimaryKey
    @ColumnInfo(name = "readingID")
    val lecturaId: Int,

    @ColumnInfo(name = "text")
    val texto: String,

    @ColumnInfo(name = "quote")
    val cita: String
)

fun LHReadingShortEntity.asExternalModel(responsorium: LHResponsoriumBrevis) = LHLectioBrevis(
    BibleBook(),
    cita,
    texto,
    responsorium
)