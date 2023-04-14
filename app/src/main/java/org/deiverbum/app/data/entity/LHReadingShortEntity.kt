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
@Entity(tableName = Constants.LH_READING_SHORT, indices = [Index(value = ["text"], unique = true)])
class LHReadingShortEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "readingID")
    var lecturaId = 0

    @JvmField
    @ColumnInfo(name = "text")
    var texto = ""

    @JvmField
    @ColumnInfo(name = "quote")
    var cita = ""
}