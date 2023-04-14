package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHIntercessionsEntity
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.model.LHIntercession

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHIntercessionsDM {
    @JvmField
    @Embedded
    var lhPatristica: LHIntercessionsJoinEntity? = null

    @JvmField
    @Relation(
        parentColumn = "intercessionFK",
        entityColumn = "intercessionID",
        entity = LHIntercessionsEntity::class
    )
    var preces: LHIntercessionsEntity? = null
    val domainModel: LHIntercession
        get() {
            val theModel = LHIntercession()
            theModel.intro = preces!!.intro
            theModel.intercession = preces!!.preces
            return theModel
        }
}