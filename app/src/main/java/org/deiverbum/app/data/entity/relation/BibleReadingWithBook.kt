package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.BibleReadingEntity
import org.deiverbum.app.data.entity.BiblieBookEntity
import org.deiverbum.app.model.Biblical
import org.deiverbum.app.model.LHOfficeBiblical
import org.deiverbum.app.model.MassReading

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class BibleReadingWithBook {
    @JvmField
    @Embedded
    var lectura: BibleReadingEntity? = null

    @JvmField
    @Relation(parentColumn = "bookFK", entityColumn = "bookID", entity = BiblieBookEntity::class)
    var libro: BiblieBookEntity? = null
    val domainModel: Biblical
        get() {
            val theModel = Biblical()
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
            theModel.book = libro!!.domainModel
            return theModel
        }
    val domainModelOficio: LHOfficeBiblical
        get() {
            val theModel = LHOfficeBiblical()
            theModel.book = libro!!.domainModel
            theModel.verseChapter = lectura!!.capitulo.toString()
            theModel.verseFrom = lectura!!.desde.toString()
            theModel.verseTo = lectura!!.hasta.toString()
            theModel.setCita(lectura!!.cita)
            theModel.text = lectura!!.texto
            return theModel
        }
}