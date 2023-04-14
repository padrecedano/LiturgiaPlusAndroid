package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHOfficeBiblicalEasterEntity
import org.deiverbum.app.data.entity.LHOfficeBiblicalEntity
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity
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
class LHOfficeBiblicalAll {
    @JvmField
    @Embedded
    var lhBiblica: LHOfficeBiblicalJoinEntity? = null

    @JvmField
    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHOfficeBiblicalEntity::class
    )
    var biblica: List<LHOfficeBiblicalWithResponsory>? = null

    @JvmField
    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHOfficeBiblicalEasterEntity::class
    )
    var biblicaE: List<LHOfficeBiblicalEasterEntity>? = null
    fun getDomainModel(tiempoId: Int?): List<LHOfficeBiblical?> {
        val theList: MutableList<LHOfficeBiblical?> = ArrayList()
        for (item in biblica!!) {
            theList.add(item.getDomainModel(tiempoId))
        }
        return theList
    }
}