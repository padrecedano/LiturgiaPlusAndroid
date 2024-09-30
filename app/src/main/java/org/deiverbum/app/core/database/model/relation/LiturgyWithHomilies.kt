package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.core.model.data.Homily

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LiturgyWithHomilies(
    @Embedded
    val join: LiturgyHomilyJoinEntity,

    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    val homilia: HomilyAll
)

fun LiturgyWithHomilies.asExternalModel(): Homily {
    return homilia.asExternalModel(join.tema)
}