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
data class TodayComentarios(
    @Embedded
    var today: TodayEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var comments: List<MisaWithComentarios>
) {

    val domainModel: Today
        get() {
            val dmToday = Today()
            dmToday.liturgyDay = liturgy.domainModel
            dmToday.todayDate = today.todayDate
            dmToday.hasSaint = today.hasSaint
            dmToday.liturgyDay.typeID = 11
            val commentList = BibleCommentList()
            var allComentarios: MutableList<List<BibleComment>> = ArrayList()
            for (item in comments) {
                allComentarios += item.domainModel
            }
            commentList.allComentarios = allComentarios.toMutableList()
            dmToday.liturgyDay.bibleCommentList = commentList
            return dmToday
        }
}