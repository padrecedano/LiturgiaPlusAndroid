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
    tableName = Constants.LH_INVITATORY_JOIN,
    foreignKeys = [ForeignKey(
        entity = LHInvitatoryEntity::class,
        parentColumns = arrayOf("caseID"),
        childColumns = arrayOf("caseFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHAntiphonEntity::class,
        parentColumns = arrayOf("antiphonID"),
        childColumns = arrayOf("antiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LHInvitatoryJoinEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId = 0

    @ColumnInfo(name = "antiphonFK", index = true)
    var salmoFK = 0

    @JvmField
    @ColumnInfo(name = "caseFK", index = true)
    var casoFK = 0
}