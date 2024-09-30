package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.database.model.entity.PaterOpusEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.Homily

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class HomilyAll(
    @Embedded
    var homilia: HomilyEntity,

    @Relation(parentColumn = "opusFK", entityColumn = "opusID", entity = PaterOpusEntity::class)
    var paterOpusAll: PaterOpusAll
)

fun HomilyAll.asExternalModel(): Homily {
    return homilia.asExternalModel(paterOpusAll.asExternalModel())
}

fun HomilyAll.asExternalModel(tema: String): Homily {
    return homilia.asExternalModel(paterOpusAll.asExternalModel(), tema)
}