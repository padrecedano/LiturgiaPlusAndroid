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
@Entity(tableName = Constants.LITURGY_TIME)
class LiturgyTimeEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timeID")
    var tiempoId = 0

    @JvmField
    @ColumnInfo(name = "timeName")
    var tiempo = ""

    @JvmField
    @ColumnInfo(name = "liturgyName")
    var liturgyName = ""
}