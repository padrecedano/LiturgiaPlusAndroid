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
class TodayVisperas {
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
    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "vHymnFK", entityColumn = "groupID")
    var himno: LHHymnWithAll? = null

    @JvmField
    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "vBiblicalFK",
        entityColumn = "groupID"
    )
    var biblica: LHReadingShortAll? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "vPsalmodyFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAll? = null

    @JvmField
    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "vIntercessionsFK",
        entityColumn = "groupID"
    )
    var lhIntercessionsDM: LHIntercessionsDM? = null

    @JvmField
    @Relation(entity = LHPrayerEntity::class, parentColumn = "vPrayerFK", entityColumn = "groupID")
    var lhPrayerAll: LHPrayerAll? = null

    @JvmField
    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "vMagnificatFK",
        entityColumn = "groupID"
    )
    var magnificat: LHGospelCanticleWithAntiphon? = null
    fun getHimno(): LHHymn? {
        return himno?.domainModel
    }

    //TODO incluir algo como hasPriority en TodayEntity
    private fun getBiblica(): BiblicalShort {
        return biblica!!.getDomainModel(today!!.tiempoId)
    }

    private fun getMagnificat(): LHGospelCanticle {
        return magnificat!!.getDomainModel(6)
    }

    val preces: LHIntercession?
        get() = lhIntercessionsDM?.domainModel

    fun getSalmodia(): LHPsalmody? {
        return salmodia?.domainModel
    }

    val oracion: Prayer?
        get() = lhPrayerAll?.domainModel

    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria.domainModel
        dm.liturgyDay.typeID = 6
        dm.liturgyPrevious = if (today!!.previoId > 1) previo?.domainModel else null
        dm.todayDate = today!!.hoy
        dm.hasSaint = today!!.hasSaint
        var previousFK = today!!.previoId
        previousFK = if (previousFK == 1) 0 else previousFK
        dm.previousFK = previousFK
        return dm
    }

    val domainModelToday: Today
        get() {
            val dm = feria.domainModel
            val dmToday = getToday()
            dm.typeID = 6
            dm.today = getToday()
            val bh = BreviaryHour()
            val visperas = Visperas()
            visperas.setIsPrevious(dmToday.previousFK)
            visperas.today = getToday()
            visperas.setHimno(getHimno())
            val psalmody=getSalmodia()
            //psalmody?.sort()
            visperas.setSalmodia(psalmody)
            visperas.setLecturaBreve(getBiblica())
            visperas.setGospelCanticle(getMagnificat())
            visperas.setPreces(preces)
            visperas.setOracion(oracion)
            bh.setVisperas(visperas)
            dm.breviaryHour = bh
            dmToday.liturgyDay = dm
            return dmToday
        }
}