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
@Entity(tableName = Constants.LH_OFFICE_VERSE, indices = [Index(value = ["verse"], unique = true)])
class LHOfficeVerseEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "verseID")
    var versoId = 0

    @ColumnInfo(name = "verse")
    var verse = ""

}