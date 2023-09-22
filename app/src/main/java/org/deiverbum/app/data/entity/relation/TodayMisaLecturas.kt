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
data class TodayMisaLecturas(
    @Embedded
    var today: TodayEntity,

    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyID"
    )
    var feria: LiturgyWithTime,

    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var previo: LiturgyWithTime,

    @Relation(
        entity = MassReadingJoinEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var joinTable: MassReadingJoinEntity,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var lecturas: List<MassReadingWithAll>
) {
    private fun getToday(): Today {
        val dm = Today()

        dm.liturgyDay = feria.domainModel
        dm.liturgyPrevious = if (today.previousFK > 1) previo.domainModel else null
        dm.todayDate = today.todayDate
        dm.hasSaint = today.hasSaint
        //dm.todayDate = hoy

        dm.setMLecturasFK(today.massReadingFK)
        return dm
    }

    val domainModel: Today
        get() {
            val dmToday = getToday()
            val dm = feria.domainModel
            dm.typeID = 10

            val mr = MassReadingList()
            mr.type = joinTable.type
            val listModel: MutableList<MassReading?> = ArrayList()
            for (item in lecturas) {
                listModel.add(item.domainModel)
            }
            listModel.sortBy { it?.getOrden() }
            mr.lecturas = listModel
            mr.today=getToday()
            //mr.sort()
            dmToday.liturgyDay = dm
            dmToday.liturgyDay.massReadingList = mr
            return dmToday
        }
}