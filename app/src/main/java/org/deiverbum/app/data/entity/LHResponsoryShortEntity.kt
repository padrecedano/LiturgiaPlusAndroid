package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.model.LHResponsoryShort
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_RESPONSORY_SHORT,
    indices = [Index(value = ["text"], unique = true)]
)
class LHResponsoryShortEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "responsoryID")
    var responsorioId = 0

    @JvmField
    @ColumnInfo(name = "text")
    var texto = ""

    @JvmField
    @ColumnInfo(name = "type")
    var tipo = 0
    fun getDomainModel(timeId: Int?): LHResponsoryShort {
        val theModel = LHResponsoryShort()
        theModel.responsoryID = responsorioId
        theModel.text = Utils.replaceByTime(texto, timeId!!)
        theModel.type = tipo
        return theModel
    }
}