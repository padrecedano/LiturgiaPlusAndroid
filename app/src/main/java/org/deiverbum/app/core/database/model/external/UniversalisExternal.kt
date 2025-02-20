package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis

/**
 * Representación de Universalis para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 * @see Universalis
 */
data class UniversalisExternal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    val liturgia: LiturgyTimeAssoc
)


fun UniversalisExternal.asExternalModel() = Universalis(
    universalis.todayDate,
    Liturgy(
        liturgia.parent.semana,
        liturgia.parent.dia,
        liturgia.parent.nombre,
        liturgia.entity.asExternalModel()
    )
)/*: Universalis {
    return universalis.asExternalModel()
}*/
