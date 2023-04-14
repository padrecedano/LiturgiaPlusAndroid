package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.BibleReadingEntity
import org.deiverbum.app.data.entity.LHOfficeBiblicalEntity
import org.deiverbum.app.data.entity.LHResponsoryEntity
import org.deiverbum.app.model.LHOfficeBiblical

/**
 *
 * Obtiene los valores para una lectura bíblica de
 * la Liturgy de las Horas,
 * desde las distintas tablas relacionadas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHOfficeBiblicalWithResponsory {
    @JvmField
    @Embedded
    var lhBiblica: LHOfficeBiblicalEntity? = null

    @JvmField
    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    var bibliaLectura: BibleReadingWithBook? = null

    @JvmField
    @Relation(
        parentColumn = "responsoryFK",
        entityColumn = "responsoryID",
        entity = LHResponsoryEntity::class
    )
    var lhResponsorio: LHResponsoryEntity? = null
    fun getDomainModel(tiempoId: Int?): LHOfficeBiblical? {
        val theModel = bibliaLectura?.domainModelOficio
        theModel!!.tema = lhBiblica!!.theme
        theModel!!.setOrden(lhBiblica!!.theOrder)
        theModel!!.responsorioLargo = lhResponsorio!!.getDomainModel(tiempoId)
        return theModel
    }
}