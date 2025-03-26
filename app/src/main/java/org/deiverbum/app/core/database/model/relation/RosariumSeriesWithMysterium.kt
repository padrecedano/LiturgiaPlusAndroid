package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.RosariumMysteriumEntity
import org.deiverbum.app.core.database.model.entity.RosariumSeriesEntity
import org.deiverbum.app.core.database.model.entity.RosariumSeriesMysteriumEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.alteri.RosariumMysteriumOrdo

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
data class RosariumSeriesWithMysterium(
    @Embedded
    val join: RosariumSeriesMysteriumEntity,

    @Relation(
        parentColumn = "seriesFK",
        entityColumn = "seriesID",
        entity = RosariumSeriesEntity::class
    )
    val series: RosariumSeriesEntity,

    @Relation(
        parentColumn = "mysteriumFK",
        entityColumn = "mysteriumID",
        entity = RosariumMysteriumEntity::class
    )
    val mysteriumm: RosariumMysteriumEntity
)

fun RosariumSeriesWithMysterium.asExternalModel() = RosariumMysteriumOrdo(
    mysterium = mysteriumm.asExternalModel(),
    ordo = join.ordo
)