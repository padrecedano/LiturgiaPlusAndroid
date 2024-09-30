package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.SaintEntity
import org.deiverbum.app.core.database.model.entity.SaintLifeEntity
import org.deiverbum.app.core.database.model.entity.SaintShortLifeEntity
import org.deiverbum.app.core.model.data.LHSanctus

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class SaintShortWithAll(
    @Embedded
    var join: LiturgySaintJoinEntity,

    @Relation(
        parentColumn = "saintFK",
        entityColumn = "saintFK",
        entity = SaintShortLifeEntity::class
    )
    var shortLife: SaintShortLifeEntity?,

    @Relation(parentColumn = "saintFK", entityColumn = "saintID", entity = SaintEntity::class)
    var saint: SaintEntity,

    @Relation(parentColumn = "saintFK", entityColumn = "saintFK", entity = SaintLifeEntity::class)
    var longLife: SaintLifeEntity
)

fun SaintShortWithAll.asExternalModel() = LHSanctus(
    saint.theName,
    if (shortLife != null && shortLife!!.shortLife != "") {
        shortLife!!.shortLife
    } else {
        longLife.martyrology
    }
)