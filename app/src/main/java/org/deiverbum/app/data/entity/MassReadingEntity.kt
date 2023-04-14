package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.model.MassReading
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.MASS_READING,
    primaryKeys = ["liturgyFK", "readingFK", "theOrder"],
    foreignKeys = [ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = BibleReadingEntity::class,
        parentColumns = arrayOf("readingID"),
        childColumns = arrayOf("readingFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class MassReadingEntity {
    @JvmField
    @ColumnInfo(name = "liturgyFK")
    var groupFK = 0

    @JvmField
    @ColumnInfo(name = "readingFK", index = true)
    var readingFK = 0

    @ColumnInfo(name = "theOrder")
    var orden = 0

    @ColumnInfo(name = "theme")
    var tema = ""
    val domainModel: MassReading
        get() {
            val theModel = MassReading()
            theModel.tema = tema
            theModel.setOrden(orden)
            return theModel
        }
}