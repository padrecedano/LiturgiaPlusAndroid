package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.model.Prayer
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.PRAYER)
class PrayerEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "prayerID")
    var oracionId = 0

    @JvmField
    @ColumnInfo(name = "prayer")
    var texto = ""

    @JvmField
    @ColumnInfo(name = "order")
    var orden = 0
    val domainModel: Prayer
        get() {
            val dm = Prayer()
            dm.prayer = texto
            return dm
        }
}