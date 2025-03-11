package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyTimeEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.liturgia.LiturgyTime

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
data class LiturgyTimeAssoc(
    @Embedded
    val parent: LiturgyEntity,

    @Relation(parentColumn = "timeFK", entityColumn = "timeID")
    val entity: LiturgyTimeEntity
)

fun LiturgyTimeAssoc.asExternalModel(): LiturgyTime = (
        entity.asExternalModel()
        )
