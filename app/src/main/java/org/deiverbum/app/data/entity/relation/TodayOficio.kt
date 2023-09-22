package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHHymnJoinEntity
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.data.entity.LHOfficePatristicEntity
import org.deiverbum.app.data.entity.LHOfficeVerseJoinEntity
import org.deiverbum.app.data.entity.LHPrayerEntity
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.LiturgySaintJoinEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.model.BreviaryHour
import org.deiverbum.app.model.LHOfficeOfReading
import org.deiverbum.app.model.LHOfficePatristic
import org.deiverbum.app.model.Oficio
import org.deiverbum.app.model.OficioEaster
import org.deiverbum.app.model.TeDeum
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodayOficio(
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

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "oHymnFK", entityColumn = "groupID")
    var hymn: LHHymnWithAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oPsalmodyFK",
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
    var patristic: List<PatristicaOficioWithResponsorio>,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "oPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll
) {

    private val getPatristic: List<LHOfficePatristic>
        get() {
            val theList: MutableList<LHOfficePatristic> = ArrayList()
            for (item in patristic) {
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
            dmToday.liturgyDay.typeID = 1
            val bh = BreviaryHour()
            if (today.oBiblicalFK == 600010101) {
                val oEaster = OficioEaster()
                oEaster.lhOfficeOfReadingEaster = officeBiblicalEaster.domainModel
                bh.setOficioEaster(oEaster)
            } else {
                val oficio = Oficio()
                val ol = LHOfficeOfReading()
                ol.biblica = officeBiblical.getDomainModel(today.timeID)
                ol.patristica = getPatristic
                ol.responsorio = officeVerse.theEntity.verse
                if (today.hasSaint == 1) {
                    oficio.santo = saint?.domainModel
                }
                oficio.invitatorio = invitatory.domainModel
                oficio.setHimno(hymn.domainModel)
                oficio.setOfficeOfReading(ol)
                oficio.setSalmodia(psalmody.domainModel)
                if (today.oTeDeum == 1) {
                    oficio.teDeum = TeDeum()
                }
                oficio.setOracion(prayer.domainModel)
                bh.setOficio(oficio)
            }
            dmToday.liturgyDay.breviaryHour = bh
            return dmToday
        }
}