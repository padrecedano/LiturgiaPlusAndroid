package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseJoinEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHOficceVerseAll(
    @Embedded
    var theJoin: LHOfficeVerseJoinEntity,

    @Relation(
        parentColumn = "verseFK",
        entityColumn = "verseID",
        entity = LHOfficeVerseEntity::class
    )
    var theEntity: LHOfficeVerseEntity
)
