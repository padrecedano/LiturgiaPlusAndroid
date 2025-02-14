package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.RosariumSeriesDiesEntity
import org.deiverbum.app.core.database.model.entity.RosariumSeriesEntity
import org.deiverbum.app.core.database.model.entity.RosariumSeriesMysteriumEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.RosariumSeriesWithMysterium
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.data.AlteriRosarium
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Rosarium
import org.deiverbum.app.core.model.data.RosariumMysteriumOrdo
import org.deiverbum.app.core.model.data.Universalis

/**
 * Representación del Rosario para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
data class RosariumExternal(
    @Embedded
    var seriesDies: RosariumSeriesDiesEntity,

    @Relation(
        entity = RosariumSeriesEntity::class,
        parentColumn = "seriesFK",
        entityColumn = "seriesID"
    )
    var series: RosariumSeriesEntity,

    @Relation(
        entity = RosariumSeriesMysteriumEntity::class,
        parentColumn = "seriesFK",
        entityColumn = "seriesFK"
    )
    var mysteriorum: List<RosariumSeriesWithMysterium>,

    )


fun RosariumExternal.asExternalModel(): Universalis {
    val tmp = mutableListOf<RosariumMysteriumOrdo>()
    mysteriorum.forEach {
        tmp.add(it.asExternalModel())
    }
    val rosarium = Rosarium(
        series = series.asExternalModel(),
        mysteriorum = tmp.sortedBy { it.ordo }
    )
    val alteri =
        AlteriRosarium(
            rosarium,
            "rosarium"
        )
    return Universalis(
        todayDate = 0,
        Liturgy(rosarium.series.series, alteri)
    )
}
