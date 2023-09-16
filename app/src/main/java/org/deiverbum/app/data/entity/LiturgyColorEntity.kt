package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`liturgy_color`** de la base de datos, que se ocupa de gestionar los colores litúrgicos.

 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LITURGY_COLOR)
data class LiturgyColorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "colorID")
    val colorID: Int,

    @ColumnInfo(name = "colorName")
    val colorName: String
)