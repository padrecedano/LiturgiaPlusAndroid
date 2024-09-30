package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleBookEntity
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.BibleBook
import org.deiverbum.app.core.model.data.LHOfficiumLectioPrior
import org.deiverbum.app.core.model.data.LHResponsorium
import org.deiverbum.app.core.model.data.LectioBiblica
import org.deiverbum.app.core.model.data.MissaeLectionum

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

data class BibleReadingWithBook(
    @Embedded
    val lectura: BibleReadingEntity,

    @Relation(parentColumn = "bookFK", entityColumn = "bookID")
    var libro: BibleBookEntity
)

/*fun BibleReadingWithBook.asExternalModelBrevis() = LHLectioBrevis(
    lectura.cita,
    lectura.texto
)*/

fun BibleReadingWithBook.asExternalModel() = LectioBiblica(
    libro.asExternalModel(),
    lectura.cita,
    lectura.texto
)

fun BibleReadingWithBook.asExternalModelOfficium(
    theOrder: Int,
    tema: String,
    responsorium: LHResponsorium
) = LHOfficiumLectioPrior(
    libro.asExternalModel(),
    lectura.cita,
    lectura.texto,
    tema,
    theOrder,
    responsorium
)

fun BibleReadingWithBook.asExternalModelMissae(theOrder: Int, tema: String) = MissaeLectionum(

    libro.asExternalModel(),
    lectura.cita,
    tema,
    lectura.texto,
    theOrder
)

fun BibleReadingWithBook.asExternalModelBook(): BibleBook {
    return libro.asExternalModel()
}

