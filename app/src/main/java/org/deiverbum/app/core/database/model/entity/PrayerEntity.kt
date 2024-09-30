package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.Oratio
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`prayer`** de la base de datos, que se ocupa de gestionar las Oraciones.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.PRAYER)
data class PrayerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "prayerID")
    val oratioId: Int,

    @ColumnInfo(name = "prayer")
    var texto: String,

    @ColumnInfo(name = "order")
    var orden: Int
) {
    val domainModel: Oratio
        get() {
            val dm = Oratio(1, "", 1)
            dm.oratio = texto
            return dm
        }
}

fun PrayerEntity.asExternalModel() = Oratio(
    oratioId,
    texto,
    orden
    //hymnID = hymnID,
    //hymn = hymn
)