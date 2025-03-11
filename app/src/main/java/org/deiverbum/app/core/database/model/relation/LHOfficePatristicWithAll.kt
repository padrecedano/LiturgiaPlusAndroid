package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.breviarium.LHOfficiumLectioAltera

/**
 *
 * Obtiene las lecturas patrísticas del Oficio de Lecturas,
 * y el responsorio correspondiente.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHOfficePatristicWithAll(
    @Embedded
    var lhPatristica: LHOfficePatristicEntity,

    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    var homilyAll: HomilyAll,

    @Relation(
        parentColumn = "responsoryFK",
        entityColumn = "responsoryID",
        entity = LHResponsoryEntity::class
    )
    var lhResponsorio: LHResponsoryEntity
)

fun LHOfficePatristicWithAll.asExternalModel() = LHOfficiumLectioAltera(
    lhPatristica.fuente,
    lhPatristica.tema,
    homilyAll.homilia.texto,
    homilyAll.paterOpusAll.asExternalModel(),
    lhResponsorio.asExternalModel(),
    lhPatristica.orden
)
