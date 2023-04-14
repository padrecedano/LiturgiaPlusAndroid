package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LiturgySaintJoinEntity
import org.deiverbum.app.data.entity.SaintEntity
import org.deiverbum.app.data.entity.SaintLifeEntity
import org.deiverbum.app.data.entity.SaintShortLifeEntity
import org.deiverbum.app.model.Saint

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class SaintShortWithAll {
    @JvmField
    @Embedded
    var liturgyEntity: LiturgySaintJoinEntity? = null

    @JvmField
    @Relation(
        parentColumn = "saintFK",
        entityColumn = "saintFK",
        entity = SaintShortLifeEntity::class
    )
    var shortLife: SaintShortLifeEntity? = null

    @JvmField
    @Relation(parentColumn = "saintFK", entityColumn = "saintID", entity = SaintEntity::class)
    var saint: SaintEntity? = null

    @JvmField
    @Relation(parentColumn = "saintFK", entityColumn = "saintFK", entity = SaintLifeEntity::class)
    var longLife: SaintLifeEntity? = null
    val domainModel: Saint
        get() {
            val dm = Saint()
            val saintLife: String
            saintLife = if (shortLife != null) {
                shortLife!!.shortLife
            } else {
                longLife!!.martyrology
            }
            dm.vida = saintLife
            dm.theName = saint!!.theName
            return dm
        }
}