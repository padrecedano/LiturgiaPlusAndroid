package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`bible_reading`** de la base de datos, que se ocupa de las lecturas bíblicas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.BIBLE_READING,
    foreignKeys = [ForeignKey(
        entity = BibleBookEntity::class,
        parentColumns = arrayOf("bookID"),
        childColumns = arrayOf("bookFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )],
    indices = [Index(value = ["bookFK", "verseChapter", "verseFrom", "verseTo"], unique = true)]
)
data class BibleReadingEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "readingID")
    var lecturaId: Int,

    @ColumnInfo(name = "bookFK")
    var libroFK: Int,

    @ColumnInfo(name = "verseChapter")
    var capitulo: Int,

    @ColumnInfo(name = "verseFrom")
    //@SerializedName("versoInicial")
    var desde: Int,

    @ColumnInfo(name = "verseTo")
    var hasta: Int,

    @ColumnInfo(name = "quote")
    var cita: String,

    @ColumnInfo(name = "text")
    var texto: String
)
