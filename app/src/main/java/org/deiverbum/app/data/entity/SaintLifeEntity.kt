package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.model.SaintLife
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.SAINT_LIFE,
    foreignKeys = [ForeignKey(
        entity = SaintEntity::class,
        parentColumns = arrayOf("saintID"),
        childColumns = arrayOf("saintFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class SaintLifeEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "saintFK")
    var saintFK = 0

    @JvmField
    @ColumnInfo(name = "longLife")
    var longLife = ""

    @JvmField
    @ColumnInfo(name = "martyrology")
    var martyrology = ""

    @JvmField
    @ColumnInfo(name = "theSource")
    var theSource = ""
    val domainModel: SaintLife
        get() {
            val theModel = SaintLife()
            theModel.longLife = longLife
            theModel.saintFK = saintFK
            theModel.martyrology = martyrology
            theModel.theSource = theSource
            return theModel
        }
}