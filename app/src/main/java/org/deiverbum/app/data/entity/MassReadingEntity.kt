package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.model.MassReading
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`mass_reading`** de la base de datos, que se ocupa de gestionar la las lecturas de la misa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.MASS_READING,
    primaryKeys = ["liturgyFK", "readingFK", "theOrder"],
    foreignKeys = [ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = BibleReadingEntity::class,
        parentColumns = arrayOf("readingID"),
        childColumns = arrayOf("readingFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class MassReadingEntity(
    @ColumnInfo(name = "liturgyFK")
    val groupFK: Int,

    @ColumnInfo(name = "readingFK", index = true)
    val readingFK: Int,

    @ColumnInfo(name = "theOrder")
    val orden: Int,

    @ColumnInfo(name = "theme")
    val tema: String
) {
    val domainModel: MassReading
        get() {
            val theModel = MassReading()
            theModel.tema = tema
            theModel.setOrden(orden)
            return theModel
        }
}