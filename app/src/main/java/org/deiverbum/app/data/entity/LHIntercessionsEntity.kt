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
@Entity(
    tableName = Constants.LH_INTERCESSIONS,
    indices = [Index(value = ["intro", "intercession"], unique = true)]
)
class LHIntercessionsEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "intercessionID")
    var precesId = 0

    @JvmField
    @ColumnInfo(name = "intro")
    var intro = ""

    @JvmField
    @ColumnInfo(name = "intercession")
    var preces = ""
}