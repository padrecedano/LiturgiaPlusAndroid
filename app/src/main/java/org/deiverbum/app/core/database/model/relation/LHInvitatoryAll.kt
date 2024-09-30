package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHAntiphonEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.LHInvitatory

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHInvitatoryAll(
    @Embedded
    val joinEntity: LHInvitatoryJoinEntity,

    @Relation(parentColumn = "caseFK", entityColumn = "caseID", entity = LHInvitatoryEntity::class)
    val psalm: LHInvitatoryWithPsalm,

    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = LHAntiphonEntity::class
    )
    val antiphon: LHAntiphonEntity
)

fun LHInvitatoryAll.asExternalModel() = LHInvitatory(
    mutableListOf(antiphon.asExternalModel()),
    mutableListOf(psalm.asExternalModel())
)