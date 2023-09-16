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
class TodayHomilias {
    @JvmField
    @Embedded
    var today: TodayEntity? = null

    @JvmField
    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime = LiturgyWithTime()

    @JvmField
    @Relation(
        entity = LiturgyHomilyJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var homilias: List<LiturgiaWithHomilias>? = null
    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria.domainModel
        dm.liturgyDay.typeID =9
        dm.todayDate = today!!.hoy
        dm.hasSaint = today!!.hasSaint
        return dm
    }

    val domainModeToday: Today
        get() {
            val dmToday : Today = getToday()
            val dm = HomilyList()
            dm.today=getToday()
            val listModel: MutableList<Homily?> = ArrayList()
            for (item in homilias!!) {
                listModel.add(item.domainModel)
            }
            dm.homilyes = listModel
            dmToday.liturgyDay.homilyList=dm
            return dmToday
        }
}