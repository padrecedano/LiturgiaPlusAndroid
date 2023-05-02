package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.model.Homily

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LiturgiaWithHomilias {
    @JvmField
    @Embedded
    var joinEntity: LiturgyHomilyJoinEntity? = null

    @JvmField
    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    var homilia: HomilyAll? = null

    val domainModel: Homily
        get() {
            val dm = homilia?.homilyDomailModel
            dm!!.tema = joinEntity!!.tema
            return dm
        }
}