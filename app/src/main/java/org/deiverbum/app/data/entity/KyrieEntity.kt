package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.model.Kyrie
import org.deiverbum.app.util.Constants

/**
 * Clase de entidad para la tabla `kyrie`.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
@Entity(tableName = Constants.KYRIE)
data class KyrieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "kyrieID")
    var kyrieID: Int,

    @JvmField
    @ColumnInfo(name = "kyrie")
    var kyrie: String
) {
    fun getDomainModel(): Kyrie {
        val dm = Kyrie()
        dm.setKyrie(kyrie)
        return dm
    }
}