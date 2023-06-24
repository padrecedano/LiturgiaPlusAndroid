package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_OFFICE_VERSE_JOIN,
    foreignKeys = [ForeignKey(
        entity = LHOfficeVerseEntity::class,
        parentColumns = arrayOf("verseID"),
        childColumns = arrayOf("verseFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LHOfficeVerseJoinEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId = 0

    @ColumnInfo(name = "verseFK", index = true)
    var responsorioFK = 0
}