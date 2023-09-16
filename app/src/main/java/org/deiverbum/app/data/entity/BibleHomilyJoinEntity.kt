package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`bible_homily_join`** de la base de datos, que se ocupa de unir las lecturas bíblicas manejadas desde [BibleReadingEntity] y las homilías relativas a esas lecturas manejadas desde [HomilyEntity].
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.BIBLE_HOMILY_JOIN,
    primaryKeys = ["readingFK", "homilyFK"],
    foreignKeys = [ForeignKey(
        entity = BibleReadingEntity::class,
        parentColumns = arrayOf("readingID"),
        childColumns = arrayOf("readingFK"),
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
data class BibleHomilyJoinEntity (
    @ColumnInfo(name = "readingFK")
    val readingFK:Int,

    @ColumnInfo(name = "homilyFK", index = true)
    val homilyFK:Int)
