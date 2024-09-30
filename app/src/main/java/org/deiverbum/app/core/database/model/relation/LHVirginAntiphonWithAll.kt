package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHVirginAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.VirginAntiphonEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHVirginAntiphonWithAll(
    @Embedded
    val theJoin: LHVirginAntiphonJoinEntity,

    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = VirginAntiphonEntity::class
    )
    val virginEntity: VirginAntiphonEntity
)