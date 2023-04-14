package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.SET_DEFAULT
import org.deiverbum.app.utils.Constants

/**
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
class HomilyEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "homilyID")
    var homiliaId = 0

    @JvmField
    @ColumnInfo(name = "opusFK", defaultValue = "0")
    var obraFK = 0

    @JvmField
    @ColumnInfo(name = "date", defaultValue = "0")
    var fecha = 0

    @JvmField
    @ColumnInfo(name = "book", defaultValue = "0")
    var libro = 0

    @JvmField
    @ColumnInfo(name = "chapter", defaultValue = "0")
    var capitulo = 0

    @JvmField
    @ColumnInfo(name = "number", defaultValue = "0")
    var numero = 0

    @JvmField
    @ColumnInfo(name = "paragraph", defaultValue = "0")
    var parrafo = 0

    @JvmField
    @ColumnInfo(name = "collectionFK", defaultValue = "0")
    var coleccionFK = 0

    @JvmField
    @ColumnInfo(name = "colNumber", defaultValue = "0")
    var colDoc = 0

    @JvmField
    @ColumnInfo(name = "colParagraph", defaultValue = "0")
    var colParrafo = 0

    @JvmField
    @ColumnInfo(name = "homily")
    var texto = ""
}