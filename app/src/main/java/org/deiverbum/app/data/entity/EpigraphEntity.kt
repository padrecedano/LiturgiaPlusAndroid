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
@Entity(tableName = Constants.LH_EPIGRAPH, indices = [Index(value = ["epigraph"], unique = true)])
class EpigraphEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "epigraphID")
    var epigrafeId = 0

    @JvmField
    @ColumnInfo(name = "epigraph")
    var epigrafe = ""
}