package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.BibleReadingEntity
import org.deiverbum.app.data.entity.BibleBookEntity
import org.deiverbum.app.model.MassReading

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class MassReadingWithBook {
    @JvmField
    @Embedded
    var lectura: BibleReadingEntity? = null

    @JvmField
    @Relation(parentColumn = "bookFK", entityColumn = "bookID", entity = BibleBookEntity::class)
    var libro: BibleBookEntity? = null
    val domainModel: MassReading
        get() {
            val theModel = MassReading()
            theModel.book = libro!!.domainModel
            theModel.verseChapter = lectura!!.capitulo.toString()
            theModel.verseFrom = lectura!!.desde.toString()
            theModel.verseTo = lectura!!.hasta.toString()
            theModel.setCita(lectura!!.cita)
            theModel.text = lectura!!.texto
            return theModel
        }
    val domainModelMisa: MassReading
        get() {
            val theModel = MassReading()
            theModel.book = libro!!.domainModel
            theModel.verseChapter = lectura!!.capitulo.toString()
            theModel.verseFrom = lectura!!.desde.toString()
            theModel.verseTo = lectura!!.hasta.toString()
            theModel.setCita(lectura!!.cita)
            theModel.text = lectura!!.texto
            return theModel
        }
}