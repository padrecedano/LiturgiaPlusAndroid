package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.SaintEntity
import org.deiverbum.app.data.entity.SaintLifeEntity
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodaySanto(
    @Embedded
    var saint: SaintEntity,

    @Relation(entity = SaintLifeEntity::class, parentColumn = "saintID", entityColumn = "saintFK")
    var saintLife: SaintLifeEntity,
) {

    val domainModel: Today
        get() {
            val dm = Today()
            dm.liturgyDay.typeID = 12
            val saintLife = saintLife.domainModel
            saintLife.dia = saint.theDay.toString()
            saintLife.mes = saint.theMonth.toString()
            saintLife.name = saint.theName
            dm.liturgyDay.saintLife = saintLife
            return dm
        }
}