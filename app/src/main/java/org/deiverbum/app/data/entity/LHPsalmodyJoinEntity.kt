package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_PSALMODY_JOIN,
    indices = [Index(value = ["groupID", "type"], unique = true)]
)
class LHPsalmodyJoinEntity {
    @PrimaryKey
    var groupID = 0

    var type = 0
}