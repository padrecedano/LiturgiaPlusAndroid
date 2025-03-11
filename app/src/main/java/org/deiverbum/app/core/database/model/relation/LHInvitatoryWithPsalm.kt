package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHInvitatoryEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmEntity
import org.deiverbum.app.core.model.data.breviarium.LHPsalm

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHInvitatoryWithPsalm(
    @Embedded
    val invitatorio: LHInvitatoryEntity,

    @Relation(
        parentColumn = "caseID",
        entityColumn = "caseFK",
        entity = LHInvitatoryJoinEntity::class
    )
    val invitatorioEntity: LHInvitatoryJoinEntity,

    @Relation(parentColumn = "psalmFK", entityColumn = "psalmID", entity = LHPsalmEntity::class)
    val psalm: LHPsalmEntity
)

fun LHInvitatoryWithPsalm.asExternalModel() = LHPsalm(
    theOrder = 0,
    true,
    psalm.salmoRef!!,
    "",
    "",
    0,
    psalm.salmo
)