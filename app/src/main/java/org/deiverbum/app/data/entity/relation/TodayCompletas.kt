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
    @Relation(entity = NightPrayerEntity::class, parentColumn = "nightPrayerFK", entityColumn = "groupID")
    var nightPrayer: NightPrayerWithAll? = null
    /*
    @JvmField
    @Relation(entity = LHHymnEntity::class, parentColumn = "hymnFK", entityColumn = "hymnID")
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
*/
    @JvmField
    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime? = null
/*
    @JvmField
    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var previo: LiturgyWithTime? = null
    */
    fun getToday(): Today {
        val dm = Today()
        val wd = today!!.weekDay
        dm.weekDay = wd - 1
        dm.liturgyDay = feria?.domainModel
        //dm.liturgyPrevious = if (today!!.previoId > 1) previo?.domainModel else null
        dm.timeID = today!!.tiempoId
        dm.todayDate = today!!.hoy
        return dm
    }
/*
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
            val dm = feria?.domainModel
            val dmToday = getToday()
            dm!!.typeID = 7
            dm!!.today = getToday()
            val bh = BreviaryHour()
            val completas = Completas()
            //visperas.setIsPrevious(dmToday.previousFK!!)
            completas.today = getToday()
            val ritosIniciales: RitosIniciales=RitosIniciales()
            val kyrie=nightPrayer?.kyrie?.domainModel
            kyrie?.setIntroduccion("Es muy de alabar que, después de la invocación inicial, se haga el examen de conciencia, el cual en la celebración comunitaria puede concluirse con un acto penitencial, de la siguiente forma:|Hermanos, habiendo llegado al final de esta jornada que Dios nos ha concedido, reconozcamos sinceramente nuestros pecados.|Todos examinan en silencio su conciencia. Terminando el examen se añade la fórmula penitencial:")
            kyrie?.setConclusion("Pueden usarse otras invocaciones penitenciales. <br />Si preside la celebración un ministro, él solo dice la absolución siguiente; en caso de lo contrario la dicen todos: <br />|El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna.∬Amén.")
            ritosIniciales?.kyrie=kyrie
            completas.setRitosIniciales(ritosIniciales)
            val hymn= LHHymn(nightPrayer?.hymn?.himno)
            completas.setHimno(hymn)
            completas.setSalmodia(nightPrayer?.salmodia?.domainModel)
            completas.biblicalShort=nightPrayer?.readingFK?.getDomainModel(today?.tiempoId)
            completas.setNuncDimitis(nightPrayer?.nuncDimitisFK?.getDomainModel(today?.tiempoId))
            //visperas.setPreces(preces)
            completas.setOracion(nightPrayer?.prayerFK?.domainModel)
            completas.setConclusion(getConclusion())
            bh.setCompletas(completas)
            dm!!.breviaryHour = bh
            dmToday.liturgyDay = dm
            val np=nightPrayer
            return dmToday
        }

    fun getBiblica(): BiblicalShort? {
        return nightPrayer!!.readingFK?.getDomainModel(today!!.tiempoId)
    }

    private fun getConclusion(): Conclusion {
        val conclusion : Conclusion = Conclusion()
        conclusion.setAntifonaVirgen(nightPrayer?.virginFK?.antiphon)
        return conclusion
    }
}