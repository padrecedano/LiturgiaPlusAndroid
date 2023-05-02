package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.data.entity.PaterOpusEntity
import org.deiverbum.app.model.Homily
import org.deiverbum.app.model.LHOfficePatristic

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class HomilyAll {
    @JvmField
    @Embedded
    var homilia: HomilyEntity? = null

    @JvmField
    @Relation(parentColumn = "opusFK", entityColumn = "opusID", entity = PaterOpusEntity::class)
    var paterOpusAll: PaterOpusAll? = null

    val patristicaDomainModel: LHOfficePatristic
        get() {
            val theModel = LHOfficePatristic()
            theModel.text = homilia!!.texto
            theModel.padre = paterOpusAll!!.paterEntity!!.padre
            theModel.obra = paterOpusAll!!.paterOpusEntity!!.opusName
            theModel.paterOpus = paterOpusAll?.domainModel
            theModel.source = homilia!!.numero.toString()
            return theModel
        }

    val homilyDomailModel: Homily
        get() {
            val theModel = Homily()
            theModel.homily = homilia!!.texto
            theModel.homilyID = homilia!!.homiliaId
            theModel.paterOpus= paterOpusAll!!.domainModel
            theModel.date= homilia!!.fecha.toString()
            return theModel
        }
}