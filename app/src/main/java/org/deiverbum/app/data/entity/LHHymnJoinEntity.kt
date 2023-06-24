package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_HYMN_JOIN,
    indices = [Index(value = ["groupID", "hymnFK"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = LHHymnEntity::class,
        parentColumns = arrayOf("hymnID"),
        childColumns = arrayOf("hymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LHHymnJoinEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId = 0

    @JvmField
    @ColumnInfo(name = "hymnFK", index = true)
    var himnoFK = 0
}