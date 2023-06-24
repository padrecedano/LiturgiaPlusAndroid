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
@Entity(tableName = Constants.LH_OFFICE_BIBLICAL_JOIN)
class LHOfficeBiblicalJoinEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    var grupoId = 0
}