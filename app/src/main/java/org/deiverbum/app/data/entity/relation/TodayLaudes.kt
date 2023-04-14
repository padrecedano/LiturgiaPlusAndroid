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
class TodayLaudes {
    @JvmField
    @Embedded
    var today: TodayEntity? = null

    @JvmField
    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var feria: LiturgyWithTime? = null

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
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "lIntercessionsFK",
        entityColumn = "groupID"
    )
    var lhIntercessionsDM: LHIntercessionsDM? = null

    @JvmField
    @Relation(entity = LHPrayerEntity::class, parentColumn = "lPrayerFK", entityColumn = "groupID")
    var lhPrayerAll: LHPrayerAll? = null

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
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var comentarios: MisaWithComentarios? = null
    fun getHimno(): LHHymn? {
        return himno?.domainModel
    }

    fun getBiblica(): BiblicalShort? {
        return biblica!!.getDomainModel(today!!.tiempoId)
    }

    fun getBenedictus(): LHGospelCanticle? {
        return benedictus!!.getDomainModel(2)
    }

    val preces: LHIntercession?
        get() = lhIntercessionsDM?.domainModel

    fun getInvitatorio(): LHInvitatory? {
        return invitatorio?.domainModel
    }

    fun getSalmodia(): LHPsalmody? {
        return salmodia?.domainModel
    }

    val oracion: Prayer?
        get() = lhPrayerAll?.domainModel

    fun getToday(): Today {
        val dm = Today()
        dm.saintFK = today!!.santoFK
        dm.liturgyDay = feria?.domainModel
        dm.todayDate = today!!.hoy
        dm.hasSaint = today!!.hasSaint
        return dm
    }

    val domainModelToday: Today
        get() {
            val dmToday = getToday()
            val dm = feria?.domainModel
            dm!!.typeID = 2
            val bh = BreviaryHour()
            val laudes = Laudes()
            laudes.setTypeId(2)
            laudes.invitatorio = getInvitatorio()
            if (dmToday.hasSaint == 1 && saint != null) {
                laudes.santo = saint?.domainModel
            }
            laudes.setHimno(getHimno())
            laudes.setSalmodia(getSalmodia())
            laudes.lecturaBreve = getBiblica()
            laudes.gospelCanticle = getBenedictus()
            laudes.preces = preces
            laudes.setOracion(oracion)
            bh.setLaudes(laudes)
            dm!!.breviaryHour = bh
            dmToday.liturgyDay = dm
            return dmToday
        }
}