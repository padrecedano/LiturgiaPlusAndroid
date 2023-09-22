package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.BibleHomilyJoinEntity
import org.deiverbum.app.data.entity.BibleReadingEntity
import org.deiverbum.app.data.entity.MassReadingEntity
import org.deiverbum.app.model.BibleComment
import org.deiverbum.app.model.MassReading

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class MisaWithComentarios(
    @Embedded
    val misaLectura: MassReadingEntity,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingFK",
        entity = BibleHomilyJoinEntity::class
    )
    val lectura: List<BibleHomilyWithAll>,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    val lecturaOne: BibleReadingWithBook
) {
    private val biblicaMisa: MassReading
        get() {
            val theModel = lecturaOne.domainModelMisa
            theModel.tema = misaLectura.tema
            theModel.setOrden(misaLectura.orden)
            return theModel
        }
    val domainModel: MutableList<BibleComment>
        get() {
            val listModel: MutableList<BibleComment> = ArrayList()
            if (lectura.isNotEmpty()) {
                for (item in lectura) {
                    val theModel = item.domainModel
                    val biblica = biblicaMisa
                    biblica.setOrden(misaLectura.orden)
                    theModel.biblica = biblica
                    listModel.add(theModel)
                }
            }
            return listModel
        }
}