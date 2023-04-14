package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHPrayerEntity
import org.deiverbum.app.data.entity.PrayerEntity
import org.deiverbum.app.model.Prayer

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHPrayerAll {
    @JvmField
    @Embedded
    var lhPrayerEntity: LHPrayerEntity? = null

    @JvmField
    @Relation(parentColumn = "prayerFK", entityColumn = "prayerID", entity = PrayerEntity::class)
    var prayerEntity: PrayerEntity? = null
    val domainModel: Prayer
        get() {
            val theModel = Prayer()
            theModel.prayer = prayerEntity!!.texto
            return theModel
        }
}