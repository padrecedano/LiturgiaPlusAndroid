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
    tableName = Constants.LH_INTERCESSIONS_JOIN,
    foreignKeys = [ForeignKey(
        entity = LHIntercessionsEntity::class,
        parentColumns = arrayOf("intercessionID"),
        childColumns = arrayOf("intercessionFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LHIntercessionsJoinEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId = 0

    @JvmField
    @ColumnInfo(name = "intercessionFK", index = true)
    var precesFK = 0
}