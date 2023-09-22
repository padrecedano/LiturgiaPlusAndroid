package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHGospelCanticleEntity
import org.deiverbum.app.data.entity.LHHymnJoinEntity
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.data.entity.LHPrayerEntity
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.LiturgySaintJoinEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.model.BreviaryHour
import org.deiverbum.app.model.Laudes
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
open class TodayLaudes(
    @Embedded
    var today: TodayEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(
        entity = LiturgySaintJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var saint: SaintShortWithAll?,

    @Relation(
        entity = LHInvitatoryJoinEntity::class,
        parentColumn = "invitatoryFK",
        entityColumn = "groupID"
    )
    var invitatory: LHInvitatoryAll,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
    var hymn: LHHymnWithAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "lPsalmodyFK",
        entityColumn = "groupID"
    )
    var psalmody: LHPsalmodyAll,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "lBiblicalFK",
        entityColumn = "groupID"
    )
    var readingShort: LHReadingShortAll,

    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "lIntercessionsFK",
        entityColumn = "groupID"
    )
    var intercessions: LHIntercessionsDM,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "lPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "lBenedictusFK",
        entityColumn = "groupID"
    )
    var gospelCanticle: LHGospelCanticleWithAntiphon
) {

    val domainModel: Today
        get() {
            val dmToday = Today()
            dmToday.saintFK = today.saintFK
            dmToday.liturgyDay = liturgy.domainModel
            dmToday.todayDate = today.todayDate
            dmToday.hasSaint = today.hasSaint
            dmToday.liturgyDay.typeID = 2
            val bh = BreviaryHour()
            val laudes = Laudes()
            laudes.invitatorio = invitatory.domainModel
            if (dmToday.hasSaint == 1 && saint != null) {
                laudes.santo = saint?.domainModel
            }
            laudes.setHimno(hymn.domainModel)
            laudes.setSalmodia(psalmody.domainModel)
            laudes.lecturaBreve = readingShort.getDomainModel(today.timeID)
            laudes.gospelCanticle = gospelCanticle.getDomainModel(2)
            laudes.preces = intercessions.domainModel
            laudes.setOracion(prayer.domainModel)
            bh.setLaudes(laudes)
            dmToday.liturgyDay.breviaryHour = bh
            return dmToday
        }
}