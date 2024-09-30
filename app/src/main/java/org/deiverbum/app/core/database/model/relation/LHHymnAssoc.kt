package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
data class LHHymnAssoc(
    @Embedded
    val join: LHHymnJoinEntity,

    @Relation(
        parentColumn = "hymnFK",
        entityColumn = "hymnID",
        entity = LHHymnEntity::class
    )
    val entity: LHHymnEntity
)