package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.BibleReadingEntity
import org.deiverbum.app.data.entity.MassReadingEntity
import org.deiverbum.app.model.MassReading

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class MassReadingWithAll {
    @JvmField
    @Embedded
    var massReadingEntity: MassReadingEntity? = null

    @JvmField
    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    var lectura: BibleReadingWithBook? = null
    val domainModel: MassReading
        get() {
            val theModel = lectura?.domainModelMisa
            theModel!!.readingID = massReadingEntity!!.readingFK
            theModel.tema = massReadingEntity!!.tema
            theModel.setOrden(massReadingEntity!!.orden)
            return theModel
        }
}