package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHReadingShortEntity
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity
import org.deiverbum.app.data.entity.LHResponsoryShortEntity
import org.deiverbum.app.model.BiblicalShort

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
class LHReadingShortAll {
    @JvmField
    @Embedded
    var lhBiblica: LHReadingShortJoinEntity? = null

    @JvmField
    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = LHReadingShortEntity::class
    )
    var biblica: LHReadingShortEntity? = null

    @JvmField
    @Relation(
        parentColumn = "responsoryFK",
        entityColumn = "responsoryID",
        entity = LHResponsoryShortEntity::class
    )
    var responsorio: LHResponsoryShortEntity? = null
    fun getDomainModel(tiempoId: Int?): BiblicalShort {
        val dm = BiblicalShort()
        dm.readingID = biblica!!.lecturaId
        dm.text = biblica!!.texto
        dm.setCita(biblica!!.cita)
        dm.setResponsorio(responsorio!!.getDomainModel(tiempoId))
        return dm
    }
}