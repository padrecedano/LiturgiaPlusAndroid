package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.KyrieEntity
import org.deiverbum.app.core.database.model.entity.LHKyrieJoinEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
data class KyrieWithAll(
    @Embedded
    val theJoin: LHKyrieJoinEntity,

    @Relation(
        parentColumn = "kyrieFK",
        entityColumn = "kyrieID",
        entity = KyrieEntity::class
    )
    val entity: KyrieEntity
)

/*fun KyrieWithAll.asExternalModel() = Kyrie (
    kyrieID,
    k
)*/
