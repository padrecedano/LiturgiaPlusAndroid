package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_THEME, indices = [Index(value = ["theme"], unique = true)])
class LHThemeEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "themeID")
    var temaId = 0

    @JvmField
    @ColumnInfo(name = "theme")
    var tema = ""
}