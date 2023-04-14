package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.DB_TABLE)
class DBTableEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tableID")
    var tableID = 0

    @ColumnInfo(name = "tableName")
    var tableName = ""
}