package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.SaintEntity
import org.deiverbum.app.data.entity.SaintLifeEntity
import org.deiverbum.app.model.Saint

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class SaintWithAll {
    @Embedded
    var santo: SaintEntity? = null

    @Relation(parentColumn = "saintID", entityColumn = "saintFK", entity = SaintLifeEntity::class)
    var lhSanto: SaintLifeEntity? = null
    val domainModelLH: Saint
        get() {
            val dm = Saint()
            dm.theName = santo!!.theName
            return dm
        }
}