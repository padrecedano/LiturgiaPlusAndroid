package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class KyrieWithAll {
    @Embedded
    var theJoin: LHKyrieJoinEntity? = null

    @Relation(
        parentColumn = "kyrieFK",
        entityColumn = "kyrieID",
        entity = KyrieEntity::class
    )
    var kyrieEntity: KyrieEntity? = null
}