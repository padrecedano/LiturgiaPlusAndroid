package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.BibleHomilyJoinEntity
import org.deiverbum.app.data.entity.BibleHomilyThemeEntity
import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.model.BibleComment

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class BibleHomilyWithAll {
    @JvmField
    @Embedded
    var bibliaLecturaEntity: BibleHomilyJoinEntity? = null

    @JvmField
    @Relation(
        parentColumn = "homilyFK",
        entityColumn = "homilyFK",
        entity = BibleHomilyThemeEntity::class
    )
    var themeEntity: BibleHomilyThemeEntity? = null

    @JvmField
    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    var homilia: HomilyAll? = null
    val domainModel: BibleComment
        get() {
            val theModel = BibleComment()
            if (themeEntity != null) {
                theModel.cita = themeEntity!!.biblical!!
                theModel.tema = themeEntity!!.theological!!
                theModel.ref = themeEntity!!.reference!!
            }
            theModel.padre = homilia!!.paterOpusAll!!.getPaterEntity()
            theModel.obra = homilia!!.paterOpusAll!!.paterOpusEntity!!.opusName
            theModel.texto = homilia!!.homilia!!.texto
            theModel.fecha = homilia!!.homilia!!.fecha.toString()
            return theModel
        }
}