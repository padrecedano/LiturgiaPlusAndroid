package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHAntiphonEntity
import org.deiverbum.app.core.database.model.entity.LHAntiphonJoinEntity
import org.deiverbum.app.core.model.data.breviarium.LHAntiphon

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
data class LHAntiphonWithAll(
    @Embedded
    var joinEntity: LHAntiphonJoinEntity,

    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = LHAntiphonEntity::class
    )
    var antiphonEntity: LHAntiphonEntity
)

fun LHAntiphonWithAll.asExternalModel() = LHAntiphon(
    antiphonID = antiphonEntity.antiphonID,
    antiphon = antiphonEntity.antiphon,
    theOrder = joinEntity.theOrder

)