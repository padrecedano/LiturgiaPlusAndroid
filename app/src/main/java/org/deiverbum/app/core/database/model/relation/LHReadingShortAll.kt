package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHReadingShortEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryShortEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.breviarium.LHLectioBrevis

/**
 *
 * Obtiene los valores para una lectura bíblica de
 * la Liturgy de las Horas,
 * desde las distintas tablas relacionadas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHReadingShortAll(

    @Embedded
    val lhBiblica: LHReadingShortJoinEntity,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = LHReadingShortEntity::class
    )
    val biblica: LHReadingShortEntity,

    @Relation(
        parentColumn = "responsoryFK",
        entityColumn = "responsoryID",
        entity = LHResponsoryShortEntity::class
    )
    val responsorio: LHResponsoryShortEntity
)

fun LHReadingShortAll.asExternalModel(): LHLectioBrevis {
    return biblica.asExternalModel(responsorio.asExternalModel())
}
