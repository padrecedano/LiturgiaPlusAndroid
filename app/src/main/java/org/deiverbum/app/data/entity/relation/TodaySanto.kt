package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.SaintEntity
import org.deiverbum.app.data.entity.SaintLifeEntity
import org.deiverbum.app.model.SaintLife
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodaySanto(
    @Embedded
    var saint: SaintEntity? = null,

    @Relation(entity = SaintLifeEntity::class, parentColumn = "saintID", entityColumn = "saintFK")
    var saintLife: SaintLife? = null,
) {
    val domainModel: SaintLife
        get() {
            val dm = SaintLife()
            dm.longLife = saintLife!!.longLife
            dm.martyrology = saintLife!!.martyrology
            dm.theSource = saintLife!!.theSource
            dm.dia = saint!!.theDay.toString()
            dm.mes = saint!!.theMonth.toString()
            dm.name = saint!!.theName
            return dm
        }

    val domainModelToday: Today
        get() {
            val dm = Today()
            val saintLife=domainModel
            dm.liturgyDay.typeID=12
            dm.liturgyDay.saintLife=saintLife
            return dm
        }
}