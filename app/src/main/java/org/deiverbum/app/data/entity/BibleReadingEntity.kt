package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import com.google.gson.annotations.SerializedName
import org.deiverbum.app.model.Biblical
import org.deiverbum.app.model.MassReading
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.BIBLE_READING,
    foreignKeys = [ForeignKey(
        entity = BiblieBookEntity::class,
        parentColumns = arrayOf("bookID"),
        childColumns = arrayOf("bookFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )],
    indices = [Index(value = ["bookFK", "verseChapter", "verseFrom", "verseTo"], unique = true)]
)
class BibleReadingEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "readingID")
    var lecturaId = 0

    @JvmField
    @ColumnInfo(name = "bookFK")
    var libroFK = 0

    @JvmField
    @ColumnInfo(name = "verseChapter")
    var capitulo = 0

    @JvmField
    @ColumnInfo(name = "verseFrom")
    @SerializedName("versoInicial")
    var desde = 0

    @JvmField
    @ColumnInfo(name = "verseTo")
    var hasta = 0

    @JvmField
    @ColumnInfo(name = "quote")
    var cita = ""

    @JvmField
    @ColumnInfo(name = "text")
    var texto = ""
    val domainModel: Biblical
        get() {
            val theModel = Biblical()
            theModel.verseChapter = capitulo.toString()
            theModel.setCita(hasta.toString())
            theModel.verseFrom = desde.toString()
            theModel.verseTo = hasta.toString()
            theModel.text = texto
            return theModel
        }
    val domainModelMisa: MassReading
        get() {
            val theModel = MassReading()
            theModel.verseChapter = capitulo.toString()
            theModel.setCita(hasta.toString())
            theModel.verseFrom = desde.toString()
            theModel.verseTo = hasta.toString()
            theModel.text = texto
            return theModel
        }
}