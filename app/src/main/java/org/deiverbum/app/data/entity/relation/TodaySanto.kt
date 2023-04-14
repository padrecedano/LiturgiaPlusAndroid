package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.SaintEntity
import org.deiverbum.app.data.entity.SaintLifeEntity
import org.deiverbum.app.model.SaintLife

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class TodaySanto {
    @JvmField
    @Embedded
    var saint: SaintEntity? = null

    @JvmField
    @Relation(entity = SaintLifeEntity::class, parentColumn = "saintID", entityColumn = "saintFK")
    var saintLife: SaintLife? = null
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
}