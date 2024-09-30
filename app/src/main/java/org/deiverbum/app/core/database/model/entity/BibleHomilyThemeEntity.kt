package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`bible_homily_theme`** de la base de datos, que se ocupa de los temas de las homilías.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.BIBLE_HOMILY_THEME,
    primaryKeys = ["homilyFK"],
    foreignKeys = [ForeignKey(
        entity = HomilyEntity::class,
        parentColumns = arrayOf("homilyID"),
        childColumns = arrayOf("homilyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class BibleHomilyThemeEntity(
    @ColumnInfo(name = "homilyFK")
    val homilyFK: Int,

    @ColumnInfo(name = "theological", defaultValue = "")
    var theological: String?,

    @ColumnInfo(name = "biblical", defaultValue = "")
    var biblical: String?,

    @ColumnInfo(name = "reference", defaultValue = "")
    var reference: String?
)