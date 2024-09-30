package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHIntercessionsEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHIntercessionsWithAll(
    @Embedded
    val join: LHIntercessionsJoinEntity,

    @Relation(
        parentColumn = "intercessionFK",
        entityColumn = "intercessionID",
        entity = LHIntercessionsEntity::class
    )
    val entity: LHIntercessionsEntity
)