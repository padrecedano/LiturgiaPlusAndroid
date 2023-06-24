package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LITURGY_COLOR)
class LiturgyColorEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "colorID")
    var colorID = 0

    @ColumnInfo(name = "colorName")
    var colorName = ""
}