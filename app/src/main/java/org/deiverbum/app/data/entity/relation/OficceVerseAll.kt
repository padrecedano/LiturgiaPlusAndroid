package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.LHOfficeVerseEntity
import org.deiverbum.app.data.entity.LHOfficeVerseJoinEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class OficceVerseAll {
    @JvmField
    @Embedded
    var theJoin: LHOfficeVerseJoinEntity? = null

    @JvmField
    @Relation(
        parentColumn = "verseFK",
        entityColumn = "verseID",
        entity = LHOfficeVerseEntity::class
    )
    var theEntity: LHOfficeVerseEntity? = null
}