package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHAntiphonEntity
import org.deiverbum.app.data.entity.LHInvitatoryEntity
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.model.LHInvitatory

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHInvitatoryAll {
    @JvmField
    @Embedded
    var salmodia: LHInvitatoryJoinEntity? = null

    @JvmField
    @Relation(parentColumn = "caseFK", entityColumn = "caseID", entity = LHInvitatoryEntity::class)
    var salmo: InvitatoryWithPsalm? = null

    @JvmField
    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = LHAntiphonEntity::class
    )
    var antifona: LHAntiphonEntity? = null
    val domainModel: LHInvitatory
        get() {
            val dm = LHInvitatory()
            dm.antiphon = antifona!!.antifona
            dm.psalm = salmo!!.getSalmo()
            dm.setRef(salmo!!.salmo!!.getSalmoRef())
            return dm
        }
}