package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.core.model.data.LHOfficiumLectioPrior

/**
 *
 * Obtiene la lectura bíblica del Oficio de Lecturas
 * con su responsorio, usando la relación expresada en [LHOfficeBiblicalWithAll].
 * Las tablas que se relacionan aquí son **`lh_office_biblical_join`** (`groupID`)  y **`lh_office_biblical`** (`groupFK`).
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHOfficeBiblicalAssoc(
    @Embedded
    var lhBiblica: LHOfficeBiblicalJoinEntity,

    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHOfficeBiblicalEntity::class
    )
    var biblica: List<LHOfficeBiblicalWithAll>
)

fun LHOfficeBiblicalAssoc.asExternalModel(): MutableList<LHOfficiumLectioPrior> {
    val emList: MutableList<LHOfficiumLectioPrior> = ArrayList()
    for (item in biblica) {
        emList.add(item.asExternalModel())
    }
    return emList
}