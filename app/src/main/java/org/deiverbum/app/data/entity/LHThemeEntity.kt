package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_theme`** de la base de datos, que se ocupa de gestionar los temas de los salmos de la Liturgia de las Horas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_THEME, indices = [Index(value = ["theme"], unique = true)])
data class LHThemeEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "themeID")
    val temaId:Int,

    @ColumnInfo(name = "theme")
    val tema:String
)