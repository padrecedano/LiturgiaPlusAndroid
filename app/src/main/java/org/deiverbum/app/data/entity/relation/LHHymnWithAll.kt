package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHHymnEntity
import org.deiverbum.app.data.entity.LHHymnJoinEntity
import org.deiverbum.app.model.LHHymn

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHHymnWithAll {
    @JvmField
    @Embedded
    var himnoJoin: LHHymnJoinEntity? = null

    @JvmField
    @Relation(parentColumn = "hymnFK", entityColumn = "hymnID", entity = LHHymnEntity::class)
    var himno: LHHymnEntity? = null
    val domainModel: LHHymn
        get() {
            val dm = LHHymn()
            dm.hymn = himno!!.himno
            return dm
        }
}