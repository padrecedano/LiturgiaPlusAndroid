package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.model.breviarium.LHAntiphon

/**
 * Obtiene las antífonas, asociando a [LHAntiphonJoinEntity] con [LHAntiphonJoinEntity]
 * la cual trae todos los elementos del salmo.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
data class LHAntiphonAssoc(
    @Embedded
    var join: LHPsalmodyJoinEntity,

    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHAntiphonJoinEntity::class
    )
    var antiphons: List<LHAntiphonWithAll>,
)

fun LHAntiphonAssoc.asExternalModel(): MutableList<LHAntiphon> {
    val emList: MutableList<LHAntiphon> = ArrayList()
    for (a in antiphons) {
        emList.add(a.asExternalModel())
    }
    return emList
}