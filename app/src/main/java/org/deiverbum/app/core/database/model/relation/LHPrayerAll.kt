package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.PrayerEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHPrayerAll(
    @Embedded
    var lhPrayerEntity: LHPrayerEntity,

    @Relation(parentColumn = "prayerFK", entityColumn = "prayerID", entity = PrayerEntity::class)
    var prayerEntity: PrayerEntity
)

fun LHPrayerAll.asExternalModel() = prayerEntity.asExternalModel()

