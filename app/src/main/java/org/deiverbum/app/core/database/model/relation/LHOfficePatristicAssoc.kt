package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicJoinEntity
import org.deiverbum.app.core.model.breviarium.LHOfficiumLectioAltera

/**
 *
 * Obtiene las lecturas patrísticas del Oficio de Lecturas,
 * asociando a [LHOfficePatristicJoinEntity] con [LHOfficePatristicWithAll].
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
data class LHOfficePatristicAssoc(
    @Embedded
    var joinEntity: LHOfficePatristicJoinEntity,

    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHOfficePatristicEntity::class
    )
    var entity: List<LHOfficePatristicWithAll> = emptyList()
)

fun LHOfficePatristicAssoc.asExternalModel(): MutableList<LHOfficiumLectioAltera> {
    val emList: MutableList<LHOfficiumLectioAltera> = ArrayList()
    for (item in entity) {
        emList.add(item.asExternalModel())
    }
    return emList
}