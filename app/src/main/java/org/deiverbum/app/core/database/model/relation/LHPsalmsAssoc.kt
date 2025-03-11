package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHPsalmJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.model.data.breviarium.LHPsalm

/**
 * Obtiene la lista de salmos, asociando a [LHPsalmodyJoinEntity] con [LHPsalmWithAll]
 * la cual trae todos los elementos del salmo.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
data class LHPsalmsAssoc(
    @Embedded
    var join: LHPsalmodyJoinEntity,

    @Relation(parentColumn = "groupID", entityColumn = "groupFK", entity = LHPsalmJoinEntity::class)
    var psalms: List<LHPsalmWithAll>,
)

fun LHPsalmsAssoc.asExternalModel(): MutableList<LHPsalm> {
    val emList: MutableList<LHPsalm> = ArrayList()
    for (ps in psalms) {
        emList.add(ps.asExternalModel())
    }
    return emList
}