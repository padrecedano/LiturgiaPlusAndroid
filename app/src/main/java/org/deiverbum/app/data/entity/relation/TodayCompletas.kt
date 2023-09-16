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
    @Embedded
    var today: TodayEntity? = null

    @Relation(entity = LHNightPrayerEntity::class, parentColumn = "nightPrayerFK", entityColumn = "groupID")
    var nightPrayer: NightPrayerWithAll? = null

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime = LiturgyWithTime()

    fun getToday(): Today {
        val dm = Today()
        val wd = today!!.weekDay
        dm.weekDay = wd - 1
        dm.liturgyDay = feria.domainModel
        dm.timeID = today!!.tiempoId
        dm.todayDate = today!!.hoy
        return dm
    }

    val domainModelToday: Today
        get() {
            val dm = feria.domainModel
            val dmToday = getToday()
            dm.typeID = 7
            dm.today = getToday()
            val bh = BreviaryHour()
            val completas = Completas()
            completas.today = getToday()
            val ritosIniciales=RitosIniciales()
            val kyrie=nightPrayer?.kyrie?.kyrieEntity?.getDomainModel()
            kyrie?.setIntroduccion("Es muy de alabar que, después de la invocación inicial, se haga el examen de conciencia, el cual en la celebración comunitaria puede concluirse con un acto penitencial, de la siguiente forma:|Hermanos, habiendo llegado al final de esta jornada que Dios nos ha concedido, reconozcamos sinceramente nuestros pecados.|Todos examinan en silencio su conciencia. Terminando el examen se añade la fórmula penitencial:")
            kyrie?.setConclusion("Pueden usarse otras invocaciones penitenciales. <br />Si preside la celebración un ministro, él solo dice la absolución siguiente; en caso de lo contrario la dicen todos: <br />|El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna.∬Amén.")
            ritosIniciales.kyrie =kyrie
            completas.setRitosIniciales(ritosIniciales)
            completas.setHimno(nightPrayer?.hymn?.domainModel)
            completas.setSalmodia(nightPrayer?.salmodia?.domainModel)
            completas.biblicalShort=nightPrayer?.readingFK?.getDomainModel(today?.tiempoId)
            completas.setNuncDimitis(nightPrayer?.nuncDimitisFK?.getDomainModel(today?.tiempoId))
            completas.setOracion(nightPrayer?.prayer?.domainModel)
            completas.setConclusion(getConclusion())
            bh.setCompletas(completas)
            dm.breviaryHour = bh
            dmToday.liturgyDay = dm
            return dmToday
        }

    private fun getConclusion(): Conclusion {
        val conclusion = Conclusion()
        conclusion.setAntifonaVirgen(nightPrayer?.virgin?.virginEntity?.antiphon)
        return conclusion
    }
}