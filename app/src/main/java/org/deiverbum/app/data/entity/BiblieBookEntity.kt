package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.model.BibleBook
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.BIBLE_BOOK)
class BiblieBookEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "bookID")
    var libroId = 0

    @JvmField
    @ColumnInfo(name = "bookType")
    var bookType = 0

    @JvmField
    @ColumnInfo(name = "shortName")
    var shortName = ""

    @JvmField
    @ColumnInfo(name = "longName")
    var longName = ""

    @JvmField
    @ColumnInfo(name = "liturgyName")
    var liturgyName = ""

    @JvmField
    @ColumnInfo(name = "orderName")
    var orderName = ""
    val domainModel: BibleBook
        get() {
            val theModel = BibleBook()
            theModel.name = longName
            theModel.liturgyName = liturgyName
            theModel.shortName = shortName
            return theModel
        }
}