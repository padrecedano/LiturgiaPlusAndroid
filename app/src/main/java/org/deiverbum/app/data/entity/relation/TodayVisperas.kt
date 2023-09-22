package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHGospelCanticleEntity
import org.deiverbum.app.data.entity.LHHymnJoinEntity
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.data.entity.LHPrayerEntity
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.model.BreviaryHour
import org.deiverbum.app.model.Today
import org.deiverbum.app.model.Visperas

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodayVisperas(
    @Embedded
    var today: TodayEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
    var hymn: LHHymnWithAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "vPsalmodyFK",
        entityColumn = "groupID"
    )
    var psalmody: LHPsalmodyAll,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "vBiblicalFK",
        entityColumn = "groupID"
    )
    var readingShort: LHReadingShortAll,

    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "vIntercessionsFK",
        entityColumn = "groupID"
    )
    var intercessions: LHIntercessionsDM,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "vPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "vMagnificatFK",
        entityColumn = "groupID"
    )
    var gospelCanticle: LHGospelCanticleWithAntiphon
) {

    val domainModelToday: Today
        get() {
            val dmToday = Today()
            dmToday.saintFK = today.saintFK
            dmToday.liturgyDay = liturgy.domainModel
            dmToday.todayDate = today.todayDate
            dmToday.hasSaint = today.hasSaint
            dmToday.liturgyDay.typeID = 6
            val bh = BreviaryHour()
            val visperas = Visperas()
            visperas.setIsPrevious(dmToday.previousFK)
            visperas.setHimno(hymn.domainModel)
            visperas.setSalmodia(psalmody.domainModel)
            visperas.setLecturaBreve(readingShort.getDomainModel(today.timeID))
            visperas.setGospelCanticle(gospelCanticle.getDomainModel(6))
            visperas.setPreces(intercessions.domainModel)
            visperas.setOracion(prayer.domainModel)
            bh.setVisperas(visperas)
            dmToday.liturgyDay.breviaryHour = bh
            return dmToday
        }
}