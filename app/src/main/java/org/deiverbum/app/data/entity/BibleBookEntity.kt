package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.model.BibleBook
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`bible_book`** de la base de datos, que se ocupa de los libros de la Biblia.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.BIBLE_BOOK)
data class BibleBookEntity (
    @PrimaryKey
    @ColumnInfo(name = "bookID")
    var libroId:Int,

    @ColumnInfo(name = "bookType")
    var bookType:Int,

    @ColumnInfo(name = "shortName")
    var shortName:String,

    @ColumnInfo(name = "longName")
    var longName:String,

    @ColumnInfo(name = "liturgyName")
    var liturgyName:String,

    @ColumnInfo(name = "orderName")
    var orderName:String
)
{
    val domainModel: BibleBook
        get() {
            val theModel = BibleBook()
            theModel.longName = longName
            theModel.liturgyName = liturgyName
            theModel.shortName = shortName
            return theModel
        }
}
