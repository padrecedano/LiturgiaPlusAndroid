package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.SET_DEFAULT
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`homily`** de la base de datos, que se ocupa de las homilías.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.HOMILY,
    foreignKeys = [ForeignKey(
        entity = PaterOpusEntity::class,
        parentColumns = arrayOf("opusID"),
        childColumns = arrayOf("opusFK"),
        onDelete = SET_DEFAULT,
        onUpdate = SET_DEFAULT
    )],
    indices = [Index(
        value = ["opusFK", "date", "book", "chapter", "number", "paragraph", "collectionFK", "colNumber", "colParagraph"],
        unique = true
    )]
)
class HomilyEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "homilyID")
    var homiliaId:Int,

    @ColumnInfo(name = "opusFK", defaultValue = "0")
    var obraFK:Int,

    @ColumnInfo(name = "date", defaultValue = "0")
    var fecha:Int,

    @ColumnInfo(name = "book", defaultValue = "0")
    var libro:Int,

    @JvmField
    @ColumnInfo(name = "chapter", defaultValue = "0")
    var capitulo:Int,

    @ColumnInfo(name = "number", defaultValue = "0")
    var numero:Int,

    @ColumnInfo(name = "paragraph", defaultValue = "0")
    var parrafo:Int,

    @ColumnInfo(name = "collectionFK", defaultValue = "0")
    var coleccionFK:Int,

    @ColumnInfo(name = "colNumber", defaultValue = "0")
    var colDoc:Int,

    @ColumnInfo(name = "colParagraph", defaultValue = "0")
    var colParrafo:Int,

    @ColumnInfo(name = "homily")
    var texto:String)
