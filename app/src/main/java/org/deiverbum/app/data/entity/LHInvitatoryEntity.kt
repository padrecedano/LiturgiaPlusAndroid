package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_INVITATORY,
    foreignKeys = [ForeignKey(
        entity = PsalmEntity::class,
        parentColumns = arrayOf("psalmID"),
        childColumns = arrayOf("psalmFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class LHInvitatoryEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "caseID")
    var casoId = 0

    @JvmField
    @ColumnInfo(name = "psalmFK", index = true)
    var salmoFK = 0
}