package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyTimeEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.liturgia.Liturgy

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LiturgyWithTime(
    @Embedded
    val liturgiaEntity: LiturgyEntity,

    @Relation(parentColumn = "timeFK", entityColumn = "timeID")
    val liturgyTime: LiturgyTimeEntity
)

fun LiturgyWithTime.asExternalModel() = Liturgy(
    liturgiaEntity.semana,
    liturgiaEntity.dia,
    //liturgiaEntity.colorFK,
    liturgiaEntity.nombre,
    liturgyTime.asExternalModel()
)
