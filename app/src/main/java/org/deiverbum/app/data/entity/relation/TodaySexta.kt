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
class TodaySexta {
    @JvmField
    @Embedded
    var today: TodayEntity? = null

    @JvmField
    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "sHymnFK", entityColumn = "groupID")
    var himno: LHHymnWithAll? = null

    @JvmField
    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "sBiblicalFK",
        entityColumn = "groupID"
    )
    var biblica: LHReadingShortAll? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "sPsalmodyFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAll? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyEntity::class,
        parentColumn = "sPsalmodyFK",
        entityColumn = "groupFK"
    )
    var salmos: List<PsalmodyWithPsalms>? = null

    @JvmField
    @Relation(entity = LHPrayerEntity::class, parentColumn = "sPrayerFK", entityColumn = "groupID")
    var lhPrayerAll: LHPrayerAll? = null

    @JvmField
    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime? = null

    @JvmField
    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var previo: LiturgyWithTime? = null
    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria?.domainModel
        dm.liturgyPrevious = if (today!!.previoId > 1) previo?.domainModel else null
        dm.todayDate = today!!.hoy
        return dm
    }

    val domainModel: Liturgy?
        get() {
            val dm = feria?.domainModel
            val bh = BreviaryHour()
            val hi = Intermedia()
            dm!!.typeID = 4
            dm!!.today = getToday()
            hi.today = getToday()
            hi.setHimno(getHimno())
            hi.setSalmodia(getSalmodia())
            hi.lecturaBreve = getBiblica()
            hi.setOracion(oracion)
            bh.setIntermedia(hi)
            dm!!.breviaryHour = bh
            return dm
        }

    fun getHimno(): LHHymn? {
        return himno?.domainModel
    }

    fun getBiblica(): BiblicalShort? {
        return biblica!!.getDomainModel(today!!.tiempoId)
    }

    fun getSalmodia(): LHPsalmody? {
        return salmodia?.domainModel
    }

    val oracion: Prayer?
        get() = lhPrayerAll?.domainModel
    val domainModelToday: Today
        get() {
            val dm = feria?.domainModel
            val bh = BreviaryHour()
            val hi = Intermedia()
            val dmToday = getToday()
            dm!!.typeID = 4
            hi.setTypeId(4)
            hi.setHimno(getHimno())
            hi.setSalmodia(getSalmodia())
            hi.lecturaBreve = getBiblica()
            hi.setOracion(oracion)
            bh.setIntermedia(hi)
            dm!!.breviaryHour = bh
            dmToday.liturgyDay = dm
            return dmToday
        }
}