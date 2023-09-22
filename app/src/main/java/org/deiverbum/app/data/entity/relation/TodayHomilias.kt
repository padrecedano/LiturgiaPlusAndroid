package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.model.Homily
import org.deiverbum.app.model.HomilyList
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodayHomilias(
    @Embedded
    var today: TodayEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(
        entity = LiturgyHomilyJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var homilyes: List<LiturgiaWithHomilias>
) {
    val domainModel: Today
        get() {
            val dmToday = Today()
            dmToday.liturgyDay = liturgy.domainModel
            dmToday.todayDate = today.todayDate
            dmToday.hasSaint = today.hasSaint
            dmToday.liturgyDay.typeID = 9
            val homilyList = HomilyList()
            val listModel: MutableList<Homily?> = ArrayList()
            for (item in homilyes) {
                listModel.add(item.domainModel)
            }
            homilyList.homilyes = listModel
            dmToday.liturgyDay.homilyList = homilyList
            return dmToday
        }
}