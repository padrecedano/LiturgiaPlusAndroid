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
class NightPrayerWithAll {
    @JvmField
    @Embedded
    var nightPrayer: NightPrayerEntity? = null

    @JvmField
    @Relation(entity = KyrieEntity::class, parentColumn = "kyrieFK", entityColumn = "kyrieID")
    var kyrie: KyrieEntity? = null

    @JvmField
    @Relation(entity = LHHymnEntity::class, parentColumn = "hymnFK", entityColumn = "hymnID")
    var hymn: LHHymnEntity? = null

    @JvmField
    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "psalmodyFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAll? = null

    @JvmField
    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "readingFK",
        entityColumn = "groupID"
    )
    var readingFK: LHReadingShortAll? = null

    @JvmField
    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "nuncDimitisFK",
        entityColumn = "groupID"
    )
    var nuncDimitisFK: LHGospelCanticleWithAntiphon? = null

        @JvmField
        @Relation(
            entity = PrayerEntity::class,
            parentColumn = "prayerFK",
            entityColumn = "prayerID"
        )
        var prayerFK: PrayerEntity? = null

    @JvmField
    @Relation(
        entity = VirginAntiphonEntity::class,
        parentColumn = "virginFK",
        entityColumn = "antiphonID"
    )
    var virginFK: VirginAntiphonEntity? = null
/*
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
    */
    val domainModelToday: Today
        get() {
            /*val dm = feria?.domainModel
            val dmToday = getToday()
            dm!!.typeID = 6
            dm!!.today = getToday()
            val bh = BreviaryHour()
            val visperas = Visperas()*/
            //visperas.setIsPrevious(dmToday.previousFK!!)
            /*visperas.today = getToday()
            visperas.setHimno(getHimno())
            visperas.setSalmodia(getSalmodia())
            visperas.setLecturaBreve(getBiblica())*/
            //visperas.setGospelCanticle(getMagnificat())
            //visperas.setPreces(preces)
            /*visperas.setOracion(oracion)
            bh.setVisperas(visperas)
            dm!!.breviaryHour = bh
            dmToday.liturgyDay = dm*/
            val np=nightPrayer
            return Today()//dmToday
        }
}