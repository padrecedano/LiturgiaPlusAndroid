package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHPsalmodyEntity
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.model.LHPsalm
import org.deiverbum.app.model.LHPsalmody

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHPsalmodyAll {
    @JvmField
    @Embedded
    var salmodia: LHPsalmodyJoinEntity? = null

    @JvmField
    @Relation(parentColumn = "groupID", entityColumn = "groupFK", entity = LHPsalmodyEntity::class)
    var salmos: List<PsalmodyWithPsalms>? = null
    val domainModel: LHPsalmody
        get() {
            val theModel = LHPsalmody()
            theModel.tipo = salmodia!!.type
            val salmosList: MutableList<LHPsalm> = ArrayList()
            for (salmo in salmos!!) {
                val s = LHPsalm()
                s.psalm = salmo.salmoText
                s.setRef(salmo.ref)
                s.antiphon = salmo.antifona
                s.theme = salmo.getTema()
                s.epigraph = salmo.getEpigrafe()
                s.part = salmo.parte
                s.theOrder = salmo.orden
                salmosList.add(s)
            }
            theModel.setSalmos(salmosList)
            theModel.sort()
            return theModel
        }
}