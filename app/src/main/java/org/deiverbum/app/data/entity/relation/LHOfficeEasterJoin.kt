package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHOfficeBiblicalEasterEntity
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.model.LHOfficeBiblicalEaster
import org.deiverbum.app.model.LHOfficeOfReadingEaster

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHOfficeEasterJoin {
    @JvmField
    @Embedded
    var biblical: LHOfficeBiblicalJoinEntity? = null

    @JvmField
    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHOfficeBiblicalEasterEntity::class
    )
    var lstEaster: List<LHOfficeEasterAll>? = null

    @JvmField
    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupID",
        entity = LHPsalmodyJoinEntity::class
    )
    var entityPsalmody: LHPsalmodyAll? = null
    val domainModel: LHOfficeOfReadingEaster
        get() {
            val theModel = LHOfficeOfReadingEaster()
            val theList: MutableList<LHOfficeBiblicalEaster?> = ArrayList()
            for (item in lstEaster!!) {
                theList.add(item.domainModel)
            }
            theList.sortBy { it?.getOrden() }

            theModel.setBiblicalE(theList)
            theModel.lhPsalmody = entityPsalmody?.domainModel
            //theModel.sort()
            return theModel
        }
}