package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHVirginAntiphonWithAll {
    @Embedded
    var theJoin: LHVirginAntiphonJoinEntity? = null

    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = VirginAntiphonEntity::class
    )
    var virginEntity: VirginAntiphonEntity? = null
}