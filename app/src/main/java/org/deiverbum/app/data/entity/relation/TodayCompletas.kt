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
class TodayCompletas {
    @JvmField
    @Embedded
    var today: TodayEntity? = null

    @JvmField
    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "tHymnFK", entityColumn = "groupID")
    var himno: LHHymnWithAll? = null

    @JvmField
    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "tBiblicalFK",
        entityColumn = "groupID"
    )
    var biblica: LHReadingShortAll? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "tPsalmodyFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAll? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyEntity::class,
        parentColumn = "tPsalmodyFK",
        entityColumn = "groupFK"
    )
    var salmos: List<PsalmodyWithPsalms>? = null

    @JvmField
    @Relation(entity = LHPrayerEntity::class, parentColumn = "tPrayerFK", entityColumn = "groupID")
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
        val wd = today!!.weekDay
        dm.weekDay = wd - 1
        dm.liturgyDay = feria?.domainModel
        dm.liturgyPrevious = if (today!!.previoId > 1) previo?.domainModel else null
        dm.timeID = today!!.tiempoId
        dm.todayDate = today!!.hoy
        return dm
    }

    val domainModel: Liturgy?
        get() {
            val dm = feria?.domainModel
            dm!!.typeID = 7
            dm!!.today = getToday()
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
}