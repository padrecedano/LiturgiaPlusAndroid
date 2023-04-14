package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.*
import org.deiverbum.app.model.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class TodayOficio {
    @JvmField
    @Embedded
    var today: TodayEntity? = null

    @JvmField
    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime? = null

    @JvmField
    @Relation(
        entity = LiturgySaintJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var saint: SaintShortWithAll? = null

    @JvmField
    @Relation(
        entity = LHInvitatoryJoinEntity::class,
        parentColumn = "invitatoryFK",
        entityColumn = "groupID"
    )
    var invitatorio: LHInvitatoryAll? = null

    @JvmField
    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "oHymnFK", entityColumn = "groupID")
    var himno: LHHymnWithAll? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oPsalmodyFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAll? = null

    @JvmField
    @Relation(
        entity = LHOfficeVerseJoinEntity::class,
        parentColumn = "oVerseFK",
        entityColumn = "groupID"
    )
    var oficioVerso: OficceVerseAll? = null

    @JvmField
    @Relation(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumn = "oBiblicalFK",
        entityColumn = "groupID"
    )
    var biblicas: LHOfficeBiblicalAll? = null

    @JvmField
    @Relation(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumn = "oBiblicalFK",
        entityColumn = "groupID"
    )
    var biblicasE: LHOfficeEasterJoin? = null

    @JvmField
    @Relation(
        entity = LHOfficePatristicEntity::class,
        parentColumn = "oPatristicFK",
        entityColumn = "groupFK"
    )
    var patristica: LHOfficePatristicWithAll? = null

    @JvmField
    @Relation(
        entity = LHOfficePatristicEntity::class,
        parentColumn = "oPatristicFK",
        entityColumn = "groupFK"
    )
    var patristicaOficioWithResponsorio: List<PatristicaOficioWithResponsorio>? = null

    @JvmField
    @Relation(entity = LHPrayerEntity::class, parentColumn = "oPrayerFK", entityColumn = "groupID")
    var lhPrayerAll: LHPrayerAll? = null
    fun getInvitatorio(): LHInvitatory? {
        return invitatorio?.domainModel
    }

    fun getHimno(): LHHymn? {
        return himno?.domainModel
    }

    fun getSalmodia(): LHPsalmody? {
        return salmodia?.domainModel
    }

    fun getOficioVerso(): String? {
        return oficioVerso?.theEntity?.verse
    }

    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria?.domainModel
        dm.todayDate = today!!.hoy
        dm.hasSaint = today!!.hasSaint
        dm.oBiblicalFK = today!!.oBiblicaFK
        return dm
    }

    fun getBiblicas(): List<LHOfficeBiblical?>? {
        return biblicas!!.getDomainModel(today!!.tiempoId)
    }

    val patristicas: List<LHOfficePatristic?>
        get() {
            val theList: MutableList<LHOfficePatristic?> = ArrayList()
            for (item in patristicaOficioWithResponsorio!!) {
                theList.add(item.getDomainModelOficio(today!!.tiempoId))
            }
            return theList
        }
    val domainModelToday: Today
        get() {
            val dm = feria?.domainModel
            dm!!.typeID = 1
            val dmToday = getToday()
            dm!!.today = dmToday
            val bh = BreviaryHour()
            if (today!!.oBiblicaFK == 600010101) {
                val oEaster = OficioEaster()
                oEaster.lhOfficeOfReadingEaster = biblicasE?.domainModel
                bh.setOficioEaster(oEaster)
            } else {
                val oficio = Oficio()
                val ol = LHOfficeOfReading()
                ol.biblica = getBiblicas()
                ol.patristica = patristicas
                ol.responsorio = getOficioVerso()
                if (dmToday.hasSaint == 1 && saint != null) {
                    oficio.santo = saint?.domainModel
                }
                oficio.invitatorio = getInvitatorio()
                oficio.setHimno(getHimno())
                oficio.setOfficeOfReading(ol)
                oficio.setSalmodia(getSalmodia())
                if (today!!.oTeDeum == 1) {
                    oficio.teDeum = TeDeum()
                }
                oficio.setOracion(lhPrayerAll?.domainModel)
                bh.setOficio(oficio)
            }
            dm!!.breviaryHour = bh
            dmToday.liturgyDay = dm
            return dmToday
        }
}