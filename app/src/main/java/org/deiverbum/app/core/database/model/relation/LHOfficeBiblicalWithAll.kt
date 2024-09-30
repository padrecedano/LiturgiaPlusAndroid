package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.LHOfficiumLectioPrior

/**
 *
 * Obtiene la lectura bíblica del Oficio de Lectura con su responsorio.
 * Las tablas que se relacionan aquí son `lh_office_biblical` (columna `readingFK`)  y `bible_reading` (columna `readingID`).
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHOfficeBiblicalWithAll(
    @Embedded
    var lhBiblica: LHOfficeBiblicalEntity,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    var bibliaLectura: BibleReadingWithBook,

    @Relation(
        parentColumn = "responsoryFK",
        entityColumn = "responsoryID",
        entity = LHResponsoryEntity::class
    )
    var lhResponsorio: LHResponsoryEntity
)

fun LHOfficeBiblicalWithAll.asExternalModel(): LHOfficiumLectioPrior {
    return bibliaLectura.asExternalModelOfficium(
        lhBiblica.theOrder,
        lhBiblica.theme,
        lhResponsorio.asExternalModel()
    )
}