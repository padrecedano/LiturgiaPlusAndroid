package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`sync_status`** de la base de datos, que se ocupa de gestionar el estado de la sincronización.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.SYNC_STATUS)
data class SyncStatusEntity (
    @PrimaryKey
    @ColumnInfo(name = "tableName")
    val tableName:String,

    @ColumnInfo(name = "versionDB")
    val versionDB:Int = 1,

    @ColumnInfo(name = "lastUpdate", defaultValue = "CURRENT_TIMESTAMP")
    val lastUpdate: String? = null)
