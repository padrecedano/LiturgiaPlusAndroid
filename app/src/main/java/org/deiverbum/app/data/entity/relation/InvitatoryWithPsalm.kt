package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHInvitatoryEntity
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.data.entity.PsalmEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class InvitatoryWithPsalm {
    @JvmField
    @Embedded
    var invitatorio: LHInvitatoryEntity? = null

    @JvmField
    @Relation(
        parentColumn = "caseID",
        entityColumn = "caseFK",
        entity = LHInvitatoryJoinEntity::class
    )
    var invitatorioEntity: LHInvitatoryJoinEntity? = null

    @JvmField
    @Relation(parentColumn = "psalmFK", entityColumn = "psalmID", entity = PsalmEntity::class)
    var salmo: PsalmEntity? = null
    fun getSalmo(): String {
        return salmo!!.salmo
    }
}