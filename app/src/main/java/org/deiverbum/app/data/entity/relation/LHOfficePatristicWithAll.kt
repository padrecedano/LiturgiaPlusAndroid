package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.data.entity.LHOfficePatristicEntity
import org.deiverbum.app.data.entity.LHResponsoryEntity
import org.deiverbum.app.model.LHOfficePatristic

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHOfficePatristicWithAll {
    @JvmField
    @Embedded
    var lhPatristica: LHOfficePatristicEntity? = null

    @JvmField
    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    var homiliaAll: HomilyAll? = null

    @JvmField
    @Relation(
        parentColumn = "responsoryFK",
        entityColumn = "responsoryID",
        entity = LHResponsoryEntity::class
    )
    var lhResponsorio: LHResponsoryEntity? = null
    fun getDomainModel(timeId: Int?): LHOfficePatristic {
        val theModel = homiliaAll?.patristicaDomainModel
        theModel!!.theme = lhPatristica!!.tema
        theModel.responsorioLargo = lhResponsorio!!.getDomainModel(timeId)
        return theModel
    }
}