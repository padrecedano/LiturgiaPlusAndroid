package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHGospelCanticleEntity
import org.deiverbum.app.data.entity.LHHymnJoinEntity
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.data.entity.LHOfficePatristicEntity
import org.deiverbum.app.data.entity.LHOfficeVerseJoinEntity
import org.deiverbum.app.data.entity.LHPrayerEntity
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.LiturgySaintJoinEntity
import org.deiverbum.app.data.entity.MassReadingEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.model.BreviaryHour
import org.deiverbum.app.model.LHOfficeOfReading
import org.deiverbum.app.model.LHOfficePatristic
import org.deiverbum.app.model.Laudes
import org.deiverbum.app.model.MassReading
import org.deiverbum.app.model.Mixto
import org.deiverbum.app.model.Oficio
import org.deiverbum.app.model.OficioEaster
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodayMixto(
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
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "lBiblicalFK",
        entityColumn = "groupID"
    )
    var readingShort: LHReadingShortAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "lPsalmodyFK",
        entityColumn = "groupID"
    )
    var psalmody: LHPsalmodyAll,

    @Relation(
        entity = LHOfficeVerseJoinEntity::class,
        parentColumn = "oVerseFK",
        entityColumn = "groupID"
    )
    var officeVerse: OficceVerseAll,

    @Relation(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumn = "oBiblicalFK",
        entityColumn = "groupID"
    )
    var officeBiblical: LHOfficeBiblicalAll,

    @Relation(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumn = "oBiblicalFK",
        entityColumn = "groupID"
    )
    var officeBiblicalEaster: LHOfficeEasterJoin,

    @Relation(
        entity = LHOfficePatristicEntity::class,
        parentColumn = "oPatristicFK",
        entityColumn = "groupFK"
    )
    var officePatristic: List<PatristicaOficioWithResponsorio>,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "lBenedictusFK",
        entityColumn = "groupID"
    )
    var gospelCanticle: LHGospelCanticleWithAntiphon,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var massReading: List<MisaWithLecturas>,

    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "lIntercessionsFK",
        entityColumn = "groupID"
    )
    var intercessions: LHIntercessionsDM,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "lPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll
) {
    private val getGospels: List<MassReading?>
        get() {
            val listModel: MutableList<MassReading?> = ArrayList()
            for (item in massReading) {
                if (item.misaLectura!!.orden >= 40) {
                    listModel.add(item.domainModel)
                }
            }
            return listModel
        }

    private val getPatristic: List<LHOfficePatristic?>
        get() {
            val theList: MutableList<LHOfficePatristic?> = ArrayList()
            for (item in officePatristic) {
                theList.add(item.getDomainModelOficio(today.timeID))
            }
            return theList
        }

    val domainModel: Today
        get() {
            val dmToday = Today()
            dmToday.saintFK = today.saintFK
            dmToday.liturgyDay = liturgy.domainModel
            dmToday.todayDate = today.todayDate
            dmToday.hasSaint = today.hasSaint
            dmToday.liturgyDay.typeID = 0
            val bh = BreviaryHour()
            if (today.oBiblicalFK == 600010101) {
                val oEaster = OficioEaster()
                oEaster.lhOfficeOfReadingEaster = officeBiblicalEaster.domainModel
                bh.setOficioEaster(oEaster)
            } else {
                val mixto = Mixto()
                val oficio = Oficio()
                val laudes = Laudes()
                if (today.hasSaint == 1) {
                    bh.santo = saint?.domainModel
                    oficio.santo = saint?.domainModel

                }
                oficio.invitatorio = invitatory.domainModel
                //laudes.invitatorio = invitatory.domainModel

                laudes.setHimno(hymn.domainModel)
                laudes.setSalmodia(psalmody.domainModel)
                laudes.lecturaBreve = readingShort.getDomainModel(today.timeID)
                laudes.gospelCanticle = gospelCanticle.getDomainModel(2)
                laudes.preces = intercessions.domainModel
                laudes.setOracion(prayer.domainModel)
                val ol = LHOfficeOfReading()
                ol.biblica = officeBiblical.getDomainModel(today.timeID)
                ol.patristica = getPatristic
                ol.responsorio = officeVerse.theEntity.verse
                oficio.setOfficeOfReading(ol)
                mixto.setOficio(oficio)
                mixto.setLaudes(laudes)
                mixto.misaLecturas = getGospels
                bh.setMixto(mixto)
                bh.setOficio(oficio)
                bh.setLaudes(laudes)
            }
            dmToday.liturgyDay.breviaryHour = bh
            return dmToday
        }
}