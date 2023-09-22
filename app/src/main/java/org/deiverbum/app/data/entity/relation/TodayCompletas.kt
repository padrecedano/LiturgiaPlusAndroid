package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHNightPrayerEntity
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.model.BreviaryHour
import org.deiverbum.app.model.Completas
import org.deiverbum.app.model.Conclusion
import org.deiverbum.app.model.RitosIniciales
import org.deiverbum.app.model.Today

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodayCompletas(
    @Embedded
    var today: TodayEntity,

    @Relation(
        entity = LHNightPrayerEntity::class,
        parentColumn = "nightPrayerFK",
        entityColumn = "groupID"
    )
    var nightPrayer: NightPrayerWithAll,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime
) {

    val domainModel: Today
        get() {
            val dmToday = Today()
            dmToday.saintFK = today.saintFK
            dmToday.liturgyDay = liturgy.domainModel
            dmToday.todayDate = today.todayDate
            dmToday.hasSaint = today.hasSaint
            dmToday.liturgyDay.typeID = 7
            val bh = BreviaryHour()
            val completas = Completas()
            val ritosIniciales = RitosIniciales()
            val kyrie = nightPrayer.kyrie!!.kyrieEntity!!.getDomainModel()
            //kyrie.setIntroduccion("Es muy de alabar que, después de la invocación inicial, se haga el examen de conciencia, el cual en la celebración comunitaria puede concluirse con un acto penitencial, de la siguiente forma:|Hermanos, habiendo llegado al final de esta jornada que Dios nos ha concedido, reconozcamos sinceramente nuestros pecados.|Todos examinan en silencio su conciencia. Terminando el examen se añade la fórmula penitencial:")
            //kyrie.setConclusion("Pueden usarse otras invocaciones penitenciales. <br />Si preside la celebración un ministro, él solo dice la absolución siguiente; en caso de lo contrario la dicen todos: <br />|El Señor todopoderoso tenga misericordia de nosotros, perdone nuestros pecados y nos lleve a la vida eterna.∬Amén.")
            ritosIniciales.kyrie = kyrie
            completas.setRitosIniciales(ritosIniciales)
            completas.setHimno(nightPrayer.hymn!!.domainModel)
            completas.setSalmodia(nightPrayer.salmodia!!.domainModel)
            completas.biblicalShort = nightPrayer.readingFK!!.getDomainModel(today.timeID)
            completas.setNuncDimitis(nightPrayer.nuncDimitisFK!!.getDomainModel(today.timeID))
            completas.setOracion(nightPrayer.prayer!!.domainModel)
            completas.setConclusion(getConclusion())
            bh.setCompletas(completas)
            dmToday.liturgyDay.breviaryHour = bh
            return dmToday
        }

    private fun getConclusion(): Conclusion {
        val conclusion = Conclusion()
        conclusion.setAntifonaVirgen(nightPrayer.virgin!!.virginEntity!!.antiphon)
        return conclusion
    }
}