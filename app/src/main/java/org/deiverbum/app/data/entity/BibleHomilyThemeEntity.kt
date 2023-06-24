package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.BIBLE_HOMILY_THEME,
    primaryKeys = ["homilyFK"],
    foreignKeys = [ForeignKey(
        entity = HomilyEntity::class,
        parentColumns = arrayOf("homilyID"),
        childColumns = arrayOf("homilyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class BibleHomilyThemeEntity {
    @JvmField
    @ColumnInfo(name = "homilyFK")
    var homilyFK = 0

    @ColumnInfo(name = "theological", defaultValue = "")
    var theological:String? = ""

    @JvmField
    @ColumnInfo(name = "biblical", defaultValue = "")
    var biblical:String? = ""

    @JvmField
    @ColumnInfo(name = "reference", defaultValue = "")
    var reference:String? = ""
}