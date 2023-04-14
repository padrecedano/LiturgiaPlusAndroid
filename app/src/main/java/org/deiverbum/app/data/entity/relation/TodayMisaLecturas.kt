package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.MassReadingEntity
import org.deiverbum.app.data.entity.MassReadingJoinEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.model.MassReading
import org.deiverbum.app.model.MassReadingList
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class TodayMisaLecturas {
    @JvmField
    @Embedded
    var today: TodayEntity? = null

    @JvmField
    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyID"
    )
    var feria: LiturgyWithTime? = null

    @JvmField
    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var previo: LiturgyWithTime? = null

    @JvmField
    @Relation(
        entity = MassReadingJoinEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var joinTable: MassReadingJoinEntity? = null

    @JvmField
    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var lecturas: List<MassReadingWithAll>? = null
    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria?.domainModel
        dm.liturgyPrevious = if (today!!.previoId > 1) previo?.domainModel else null
        dm.todayDate = today!!.hoy
        dm.hasSaint = today!!.hasSaint
        dm.setMLecturasFK(today!!.mLecturasFK)
        return dm
    }

    val domainModel: MassReadingList
        get() {
            val dm = MassReadingList()
            dm.today = getToday()
            dm.type = joinTable!!.type
            val listModel: MutableList<MassReading?> = ArrayList()
            for (item in lecturas!!) {
                listModel.add(item.domainModel)
            }
            listModel.sortBy{it?.getOrden()}
            dm.lecturas = listModel
            dm.sort()
            return dm
        }
}