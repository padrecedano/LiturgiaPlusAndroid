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
@Entity(tableName = Constants.LH_HYMN, indices = [Index(value = ["hymn"], unique = true)])
class LHHymnEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hymnID")
    var himnoId = 0

    @JvmField
    @ColumnInfo(name = "hymn")
    var himno = ""
}