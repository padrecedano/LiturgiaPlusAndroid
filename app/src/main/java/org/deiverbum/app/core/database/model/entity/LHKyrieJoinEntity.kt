package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_KYRIE_JOIN,
    indices = [Index(value = ["groupID", "kyrieFK"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = KyrieEntity::class,
        parentColumns = arrayOf("kyrieID"),
        childColumns = arrayOf("kyrieFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LHKyrieJoinEntity {
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var groupID = 0

    @ColumnInfo(name = "kyrieFK", index = true)
    var kyrieFK = 0

}