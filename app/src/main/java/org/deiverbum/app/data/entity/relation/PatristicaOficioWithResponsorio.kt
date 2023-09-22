package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.data.entity.LHOfficePatristicEntity
import org.deiverbum.app.data.entity.LHResponsoryEntity
import org.deiverbum.app.model.LHOfficePatristic

/**
 *
 * Obtiene los valores para una lectura bíblica de
 * la Liturgia de las Horas,
 * desde las distintas tablas relacionadas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class PatristicaOficioWithResponsorio {
    @JvmField
    @Embedded
    var lhPatristica: LHOfficePatristicEntity? = null

    @JvmField
    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    var homilyAll: HomilyAll? = null

    @JvmField
    @Relation(
        parentColumn = "responsoryFK",
        entityColumn = "responsoryID",
        entity = LHResponsoryEntity::class
    )
    var lhResponsorio: LHResponsoryEntity? = null
    fun getDomainModelOficio(tiempoId: Int?): LHOfficePatristic {
        try {
            val theModel = homilyAll?.patristicaDomainModel
            theModel!!.paterOpus = homilyAll!!.paterOpusAll!!.domainModel
            theModel.theme = lhPatristica!!.tema
            theModel.source = lhPatristica!!.fuente
            theModel.theOrder = lhPatristica!!.orden
            theModel.responsorioLargo = lhResponsorio!!.getDomainModel(tiempoId)
            return theModel
        } catch (e: Exception) {
            //Log.e("ERR", e.message!!)
        }
        return LHOfficePatristic()
    }
}