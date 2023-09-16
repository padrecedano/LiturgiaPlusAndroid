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
class TodayMixto {
    @JvmField
    @Embedded
    var today: TodayEntity? = null

    @JvmField
    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime = LiturgyWithTime()

    @JvmField
    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var previo: LiturgyWithTime? = null

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
    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
    var himno: LHHymnWithAll? = null

    @JvmField
    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "lBiblicalFK",
        entityColumn = "groupID"
    )
    var biblica: LHReadingShortAll? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "lPsalmodyFK",
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
    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "lBenedictusFK",
        entityColumn = "groupID"
    )
    var benedictus: LHGospelCanticleWithAntiphon? = null

    @JvmField
    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var lecturas: List<MisaWithLecturas>? = null

    @JvmField
    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "lIntercessionsFK",
        entityColumn = "groupID"
    )
    var lhIntercessionsDM: LHIntercessionsDM? = null

    @JvmField
    @Relation(entity = LHPrayerEntity::class, parentColumn = "lPrayerFK", entityColumn = "groupID")
    var lhPrayerAll: LHPrayerAll? = null
    private val evangelios: List<MassReading?>
        get() {
            val listModel: MutableList<MassReading?> = ArrayList()
            for (item in lecturas!!) {
                if (item.misaLectura!!.orden >= 40) {
                    listModel.add(item.domainModel)
                }
            }
            return listModel
        }

    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria.domainModel
        dm.liturgyPrevious = if (today!!.previoId > 1) previo?.domainModel else null
        dm.todayDate = today!!.hoy
        dm.hasSaint = today!!.hasSaint
        dm.oBiblicalFK = today!!.oBiblicaFK
        return dm
    }

    private fun getHimno(): LHHymn? {
        return himno?.domainModel
    }

    //TODO incluir algo como hasPriority en TodayEntity
    private fun getBiblica(): BiblicalShort {
        return biblica!!.getDomainModel(today!!.tiempoId)
    }

    private fun getBenedictus(): LHGospelCanticle {
        return benedictus!!.getDomainModel(2)
    }

    val preces: LHIntercession?
        get() = lhIntercessionsDM?.domainModel

    private fun getInvitatorio(): LHInvitatory? {
        return invitatorio?.domainModel
    }

    private fun getSalmodia(): LHPsalmody? {
        return salmodia?.domainModel
    }

    val oracion: Prayer?
        get() = lhPrayerAll?.domainModel

    private fun getBiblicas(): List<LHOfficeBiblical?> {
        return biblicas!!.getDomainModel(today!!.tiempoId)
    }


    private val patristicas: List<LHOfficePatristic?>
        get() {
            val theList: MutableList<LHOfficePatristic?> = ArrayList()
            for (item in patristicaOficioWithResponsorio!!) {
                theList.add(item.getDomainModelOficio(today!!.tiempoId))
            }
            return theList
        }

    private fun getOficioVerso(): String? {
        return oficioVerso?.theEntity?.verse
    }

    //mixto.setInvitatorio(getInvitatorio());
    val domainModelToday: Today
        get() {
            val dm = feria.domainModel
            dm.typeID = 0
            val dmToday = getToday()
            dm.today = dmToday
            val bh = BreviaryHour()
            if (today!!.oBiblicaFK == 600010101) {
                val oEaster = OficioEaster()
                oEaster.lhOfficeOfReadingEaster = biblicasE?.domainModel
                bh.setOficioEaster(oEaster)
            } else {
                val mixto = Mixto()
                val oficio = Oficio()
                val laudes = Laudes()
                mixto.setTypeId(0)
                if (dmToday.hasSaint == 1 && saint != null) {
                    bh.santo = saint?.domainModel
                }
                //mixto.setInvitatorio(getInvitatorio());
                oficio.invitatorio = getInvitatorio()
                laudes.setHimno(getHimno())
                laudes.setSalmodia(getSalmodia())
                laudes.lecturaBreve = getBiblica()
                laudes.gospelCanticle = getBenedictus()
                laudes.preces = preces
                laudes.setOracion(oracion)
                val ol = LHOfficeOfReading()
                ol.biblica = getBiblicas() as List<LHOfficeBiblical>?
                ol.patristica = patristicas
                ol.responsorio = getOficioVerso()
                oficio.setOfficeOfReading(ol)
                laudes.lecturaBreve = getBiblica()
                laudes.gospelCanticle = getBenedictus()
                laudes.preces = preces
                laudes.setOracion(oracion)
                mixto.setOficio(oficio)
                mixto.setLaudes(laudes)
                mixto.misaLecturas = evangelios
                bh.setMixto(mixto)
                bh.setOficio(oficio)
                bh.setLaudes(laudes)
            }
            dm.breviaryHour = bh
            dmToday.liturgyDay = dm
            return dmToday
        }
}