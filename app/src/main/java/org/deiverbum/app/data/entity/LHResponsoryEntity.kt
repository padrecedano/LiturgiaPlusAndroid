package org.deiverbum.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.deiverbum.app.model.LHResponsory
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(tableName = Constants.LH_RESPONSORY)
class LHResponsoryEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "responsoryID")
    var responsorioId = 0

    @JvmField
    @ColumnInfo(name = "text")
    var texto = ""

    @JvmField
    @ColumnInfo(name = "source")
    var fuente = ""

    @JvmField
    @ColumnInfo(name = "type")
    var tipo = 0
    fun getDomainModel(timeId: Int?): LHResponsory {
        val theModel = LHResponsory()
        theModel.text = Utils.replaceByTime(texto, timeId!!)
        theModel.type = tipo
        theModel.source = fuente
        return theModel
    }
}