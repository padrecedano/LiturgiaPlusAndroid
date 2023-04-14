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
@Entity(tableName = Constants.LH_ANTIPHON, indices = [Index(value = ["antiphon"], unique = true)])
class LHAntiphonEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "antiphonID")
    var antifonaId = 0

    @JvmField
    @ColumnInfo(name = "antiphon")
    var antifona = ""
}