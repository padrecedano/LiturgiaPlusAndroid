package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`liturgy_homily_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de las homilías.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LITURGY_HOMILY_JOIN,
    primaryKeys = ["liturgyFK", "homilyFK"],
    foreignKeys = [ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = HomilyEntity::class,
        parentColumns = arrayOf("homilyID"),
        childColumns = arrayOf("homilyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LiturgyHomilyJoinEntity(
    @ColumnInfo(name = "liturgyFK")
    var liturgiaFK: Int,

    @ColumnInfo(name = "homilyFK", index = true)
    var homiliaFK: Int,

    @ColumnInfo(name = "theme", defaultValue = "")
    var tema: String
)