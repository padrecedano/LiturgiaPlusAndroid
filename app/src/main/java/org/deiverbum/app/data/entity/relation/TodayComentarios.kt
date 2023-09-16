package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.MassReadingEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.model.BibleComment
import org.deiverbum.app.model.BibleCommentList
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class TodayComentarios {
    @Embedded
    var today: TodayEntity? = null

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime = LiturgyWithTime()

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var comentarios: List<MisaWithComentarios>? = null

    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria.domainModel
        //dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.todayDate = today!!.hoy
        dm.hasSaint = today!!.hasSaint
        return dm
    }

    val domainModelToday: Today
        get() {
            val dm = feria.domainModel
            val dmToday = getToday()
            dm.typeID = 11
            val commentList = BibleCommentList()
            commentList.setHoy(getToday())
            val allComentarios: MutableList<List<BibleComment?>> = ArrayList()
            for (item in comentarios!!) {
                allComentarios+=item.domainModel
            }
            commentList.allComentarios = allComentarios
            dm.bibleCommentList = commentList
            dmToday.liturgyDay = dm
            return dmToday
        }

}