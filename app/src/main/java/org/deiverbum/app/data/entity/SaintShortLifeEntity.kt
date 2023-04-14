package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.model.SaintLife
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.SAINT_SHORT_LIFE,
    foreignKeys = [ForeignKey(
        entity = SaintEntity::class,
        parentColumns = arrayOf("saintID"),
        childColumns = arrayOf("saintFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class SaintShortLifeEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "saintFK")
    var saintFK = 0

    @JvmField
    @ColumnInfo(name = "shortLife", defaultValue = "")
    var shortLife = ""
    val domainModel: SaintLife
        get() {
            val theModel = SaintLife()
            theModel.shortLife = shortLife
            theModel.saintFK = saintFK
            return theModel
        }
}