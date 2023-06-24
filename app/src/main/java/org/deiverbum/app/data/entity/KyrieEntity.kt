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
class KyrieEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "kyrieID")
    var kyrieID = 0

    @JvmField
    @ColumnInfo(name = "kyrie")
    var kyrie = ""

    val domainModel: Kyrie
        get() {
            val dm = Kyrie()
            dm.setTexto(kyrie)
            return dm
        }
}