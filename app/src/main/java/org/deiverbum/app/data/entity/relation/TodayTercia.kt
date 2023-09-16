package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.*
import org.deiverbum.app.model.*

/**
 * Obtiene el contenido de la hora **`Tercia`** desde la base de datos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodayTercia (

    @Embedded
    var today: TodayEntity? = null,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "tHymnFK", entityColumn = "groupID")
    var himno: LHHymnWithAll? = null,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "tBiblicalFK",
        entityColumn = "groupID"
    )
    var biblica: LHReadingShortAll? = null,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "tPsalmodyFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAll? = null,

    @Relation(
        entity = LHPsalmodyEntity::class,
        parentColumn = "tPsalmodyFK",
        entityColumn = "groupFK"
    )
    var salmos: List<PsalmodyWithPsalms>? = null,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "tPrayerFK", entityColumn = "groupID")
    var lhPrayerAll: LHPrayerAll? = null,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime = LiturgyWithTime(),

    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var previo: LiturgyWithTime? = null)
{
    fun getToday(): Today {
        val dm = Today()
        dm.liturgyDay = feria.domainModel
        dm.liturgyPrevious = if (today!!.previoId > 1) previo?.domainModel else null
        dm.todayDate = today!!.hoy
        return dm
    }

    val domainModel: Liturgy
        get() {
            val dm = feria.domainModel
            val bh = BreviaryHour()
            val hi = Intermedia()
            dm.typeID = 3
            dm.today = getToday()
            hi.today = getToday()
            hi.setHimno(getHimno())
            hi.setSalmodia(getSalmodia())
            hi.lecturaBreve = getBiblica()
            hi.setOracion(oracion)
            bh.setIntermedia(hi)
            dm.breviaryHour = bh
            return dm
        }

    private fun getHimno(): LHHymn? {
        return himno?.domainModel
    }

    private fun getBiblica(): BiblicalShort {
        return biblica!!.getDomainModel(today!!.tiempoId)
    }

    private fun getSalmodia(): LHPsalmody? {
        return salmodia?.domainModel
    }

    val oracion: Prayer?
        get() = lhPrayerAll?.domainModel
    val domainModelToday: Today
        get() {
            val dm = feria.domainModel
            val bh = BreviaryHour()
            val hi = Intermedia()
            val dmToday = getToday()
            dm.typeID = 3
            hi.setTypeId(3)
            hi.setHimno(getHimno())
            hi.setSalmodia(getSalmodia())
            hi.lecturaBreve = getBiblica()
            hi.setOracion(oracion)
            bh.setIntermedia(hi)
            dm.breviaryHour = bh
            dmToday.liturgyDay = dm
            return dmToday
        }
}