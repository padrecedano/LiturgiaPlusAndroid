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
import org.deiverbum.app.model.LHPsalmody
import org.deiverbum.app.model.Oficio
import org.deiverbum.app.model.OficioEaster
import org.deiverbum.app.model.TeDeum
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class TodayOficio {
    @Embedded
    var today: TodayEntity? = null

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime = LiturgyWithTime()

    @Relation(
        entity = LiturgySaintJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var saint: SaintShortWithAll? = null

    @Relation(
        entity = LHInvitatoryJoinEntity::class,
        parentColumn = "invitatoryFK",
        entityColumn = "groupID"
    )
    var invitatorio: LHInvitatoryAll? = null

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "oHymnFK", entityColumn = "groupID")
    var himno: LHHymnWithAll? = null

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oPsalmodyFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAll? = null

    @Relation(
        entity = LHOfficeVerseJoinEntity::class,
        parentColumn = "oVerseFK",
        entityColumn = "groupID"
    )
    var oficioVerso: OficceVerseAll? = null

    @Relation(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumn = "oBiblicalFK",
        entityColumn = "groupID"
    )
    var biblicas: LHOfficeBiblicalAll? = null

    @Relation(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumn = "oBiblicalFK",
        entityColumn = "groupID"
    )
    var biblicasE: LHOfficeEasterJoin? = null

    @Relation(
        entity = LHOfficePatristicEntity::class,
        parentColumn = "oPatristicFK",
        entityColumn = "groupFK"
    )
    var patristica: LHOfficePatristicWithAll? = null

    @Relation(
        entity = LHOfficePatristicEntity::class,
        parentColumn = "oPatristicFK",
        entityColumn = "groupFK"
    )
    var patristicaOficioWithResponsorio: List<PatristicaOficioWithResponsorio>? = null

    @Relation(entity = LHPrayerEntity::class, parentColumn = "oPrayerFK", entityColumn = "groupID")
    var lhPrayerAll: LHPrayerAll? = null

    private fun getSalmodia(): LHPsalmody? {
        return salmodia?.domainModel
    }

    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria.domainModel
        dm.todayDate = today!!.hoy
        dm.hasSaint = today!!.hasSaint
        dm.oBiblicalFK = today!!.oBiblicaFK
        return dm
    }

    private val patristicas: List<LHOfficePatristic?>
        get() {
            val theList: MutableList<LHOfficePatristic?> = ArrayList()
            for (item in patristicaOficioWithResponsorio!!) {
                theList.add(item.getDomainModelOficio(today!!.tiempoId))
            }
            return theList
        }
    val domainModelToday: Today
        get() {
            //getSalmodiaa()
            val dm = feria.domainModel
            dm.typeID = 1
            val dmToday = getToday()
            dm.today = dmToday
            val bh = BreviaryHour()
            if (today!!.oBiblicaFK == 600010101) {
                val oEaster = OficioEaster()
                oEaster.lhOfficeOfReadingEaster = biblicasE?.domainModel
                bh.setOficioEaster(oEaster)
            } else {
                val oficio = Oficio()
                val ol = LHOfficeOfReading()
                ol.biblica = biblicas!!.getDomainModel(today!!.tiempoId)
                ol.patristica = patristicas
                ol.responsorio = oficioVerso?.theEntity?.verse
                if (dmToday.hasSaint == 1 && saint != null) {
                    oficio.santo = saint?.domainModel
                }
                oficio.invitatorio = invitatorio?.domainModel
                oficio.setHimno(himno?.domainModel)
                oficio.setOfficeOfReading(ol)
                oficio.setSalmodia(getSalmodia())
                if (today!!.oTeDeum == 1) {
                    oficio.teDeum = TeDeum()
                }
                oficio.setOracion(lhPrayerAll?.domainModel)
                bh.setOficio(oficio)
            }
            dm.breviaryHour = bh
            dmToday.liturgyDay = dm
            return dmToday
        }
}