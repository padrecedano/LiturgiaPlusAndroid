package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.PaterEntity
import org.deiverbum.app.data.entity.PaterOpusEntity
import org.deiverbum.app.model.PaterOpus

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class PaterOpusAll {
    @JvmField
    @Embedded
    var paterOpusEntity: PaterOpusEntity? = null

    @JvmField
    @Relation(parentColumn = "paterFK", entityColumn = "paterID", entity = PaterEntity::class)
    var paterEntity: PaterEntity? = null
    fun getPaterEntity(): String {
        return paterEntity!!.getLiturgyName()
    }

    val domainModel: PaterOpus
        get() {
            val dm = PaterOpus()
            dm.pater = paterEntity!!.domainModel
            dm.opusName = paterOpusEntity!!.opusName
            dm.liturgyName = paterOpusEntity!!.liturgyName
            return dm
        }
}