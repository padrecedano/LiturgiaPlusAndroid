package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHAntiphonEntity
import org.deiverbum.app.data.entity.LHGospelCanticleEntity
import org.deiverbum.app.model.LHGospelCanticle

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHGospelCanticleWithAntiphon {
    @JvmField
    @Embedded
    var ce: LHGospelCanticleEntity? = null

    @JvmField
    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = LHAntiphonEntity::class
    )
    var antifona: LHAntiphonEntity? = null
    fun getAntifona(): String {
        return antifona!!.antifona
    }

    fun getDomainModel(tipo: Int?): LHGospelCanticle {
        val ce = LHGospelCanticle()
        ce.typeID = tipo!!
        ce.antiphon = getAntifona()
        return ce
    }
}