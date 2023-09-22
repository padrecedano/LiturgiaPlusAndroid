package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.LiturgyTimeEntity
import org.deiverbum.app.model.Liturgy

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LiturgyWithTimeNew(
    @Embedded
    val liturgyEntity: LiturgyEntity,

    @Relation(
        parentColumn = "timeFK", entityColumn = "timeID"
        //associateBy = Junction(LiturgyWithTimeCrossRef::class)
    )
    val tiempo: LiturgyTimeEntity
) {
    val domainModel: Liturgy
        get() {
            val dm = Liturgy()
            /*dm.liturgyID = liturgyEntity!!.liturgiaId
            dm.liturgyID = liturgyEntity!!.liturgiaId
            dm.semana = liturgyEntity!!.semana
            dm.dia = liturgyEntity!!.dia
            dm.colorFK = liturgyEntity!!.colorFK
            dm.name = liturgyEntity!!.nombre
            val t = LiturgyTime()
            t.timeID = tiempo!!.tiempoId
            t.timeName = tiempo!!.tiempo
            t.liturgyName = tiempo!!.liturgyName
            dm.liturgyTime = t*/
            return dm
        }
}