package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.*
import org.deiverbum.app.model.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
class TodayNona {
    @JvmField
    @Embedded
    var today: TodayEntity? = null

    @JvmField
    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime? = null

    @JvmField
    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "nHymnFK", entityColumn = "groupID")
    var himno: LHHymnWithAll? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "nPsalmodyFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAll? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyEntity::class,
        parentColumn = "nPsalmodyFK",
        entityColumn = "groupFK"
    )
    var salmos: List<PsalmodyWithPsalms>? = null

    @JvmField
    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "nBiblicalFK",
        entityColumn = "groupID"
    )
    var biblica: LHReadingShortAll? = null

    @JvmField
    @Relation(entity = LHPrayerEntity::class, parentColumn = "nPrayerFK", entityColumn = "groupID")
    var lhPrayerAll: LHPrayerAll? = null
    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria?.domainModel
        dm.todayDate = today!!.hoy
        return dm
    }

    //hi.setHourId(5);
    val domainModel: Liturgy?
        get() {
            val dm = feria?.domainModel
            val bh = BreviaryHour()
            val hi = Intermedia()
            dm!!.typeID = 5
            //hi.setHourId(5);
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
            dm!!.typeID = 5
            hi.setTypeId(5)
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