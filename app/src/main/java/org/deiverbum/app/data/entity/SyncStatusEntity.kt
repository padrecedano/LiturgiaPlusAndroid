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
@Entity(tableName = Constants.SYNC_STATUS)
class SyncStatusEntity {
    @PrimaryKey
    @ColumnInfo(name = "tableName")
    var tableName = ""

    @ColumnInfo(name = "versionDB")
    var versionDB = 1

    @ColumnInfo(name = "lastUpdate", defaultValue = "CURRENT_TIMESTAMP")
    var lastUpdate: String? = null
}