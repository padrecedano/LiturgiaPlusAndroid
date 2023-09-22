package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHHymnJoinEntity
import org.deiverbum.app.data.entity.LHPrayerEntity
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.model.BreviaryHour
import org.deiverbum.app.model.Intermedia
import org.deiverbum.app.model.Today

/**
 * Obtiene el contenido de la hora **`Nona`** desde la base de datos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

data class TodayNona (
    @Embedded
    var today: TodayEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "nHymnFK", entityColumn = "groupID")
    var hymn: LHHymnWithAll,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "nBiblicalFK",
        entityColumn = "groupID"
    )
    var readingShort: LHReadingShortAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "nPsalmodyFK",
        entityColumn = "groupID"
    )
    var psalmody: LHPsalmodyAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "nPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll,
) {
    val domainModel: Today
        get() {
            val dmToday = Today()
            dmToday.saintFK = today.saintFK
            dmToday.liturgyDay = liturgy.domainModel
            dmToday.todayDate = today.todayDate
            dmToday.hasSaint = today.hasSaint
            dmToday.liturgyDay.typeID = 5
            val bh = BreviaryHour()
            val hi = Intermedia()
            hi.setHimno(hymn.domainModel)
            hi.setSalmodia(psalmody.domainModel)
            hi.lecturaBreve = readingShort.getDomainModel(today.timeID)
            hi.setOracion(prayer.domainModel)
            bh.setIntermedia(hi)
            dmToday.liturgyDay.breviaryHour = bh
            return dmToday
        }
}