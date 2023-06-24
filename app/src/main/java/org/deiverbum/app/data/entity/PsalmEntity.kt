package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.model.LHPsalm
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_PSALM)
class PsalmEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "psalmID")
    var salmoId = 0

    @JvmField
    @ColumnInfo(name = "psalm")
    var salmo = ""

    @JvmField
    @ColumnInfo(name = "readingID")
    var pericopaId = 0

    @JvmField
    @ColumnInfo(name = "quote")
    var salmoRef: String? = null
    fun getSalmoRef(): String {
        return if (salmoRef != null) salmoRef!! else ""
    }

    fun setSalmoRef(salmoRef: String?) {
        this.salmoRef = salmoRef
    }

    val domainModel: LHPsalm
        get() {
            val dm = LHPsalm()
            dm.psalm = salmo
            dm.setRef(getSalmoRef())
            return dm
        }
}