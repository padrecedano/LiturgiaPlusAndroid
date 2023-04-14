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
class MisaWithLecturas {
    @JvmField
    @Embedded
    var misaLectura: MassReadingEntity? = null

    @JvmField
    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    var lectura: MassReadingWithBook? = null
    val domainModel: MassReading?
        get() {
            val theModel = lectura?.domainModelMisa
            theModel!!.tema = misaLectura!!.tema
            theModel!!.setOrden(misaLectura!!.orden)
            return theModel
        }
}