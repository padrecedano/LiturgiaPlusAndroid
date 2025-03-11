package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.traditio.PaterOpus
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`pater_opus`** de la base de datos, que se ocupa de gestionar las obras de los Padres de la Iglesia.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.PATER_OPUS,
    foreignKeys = [ForeignKey(
        entity = PaterEntity::class,
        parentColumns = arrayOf("paterID"),
        childColumns = arrayOf("paterFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )],
    indices = [Index(value = ["opusName", "paterFK", "volume"], unique = true)]
)
data class PaterOpusEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "opusID")
    val obraId: Int,

    @ColumnInfo(name = "opusName")
    val opusName: String,

    @ColumnInfo(name = "liturgyName", defaultValue = "")
    val liturgyName: String,

    @ColumnInfo(name = "subTitle", defaultValue = "NULL")
    var subTitle: String? = null,

    @ColumnInfo(name = "volume", defaultValue = "NULL")
    var volumen: Int? = null,

    @ColumnInfo(name = "opusDate", defaultValue = "NULL")
    var opusDate: Int? = null,

    @ColumnInfo(name = "editorial", defaultValue = "NULL")
    var editorial: String? = null,

    @ColumnInfo(name = "city", defaultValue = "NULL")
    var ciudad: String? = null,

    @ColumnInfo(name = "opusYear", defaultValue = "NULL")
    var opusYear: Int? = null,

    @ColumnInfo(name = "paterFK", defaultValue = "0", index = true)
    val padreFK: Int,

    @ColumnInfo(name = "typeFK", defaultValue = "0")
    var typeFK: Int,

    @ColumnInfo(name = "collectionFK", defaultValue = "0")
    var collectionFK: Int
)

fun PaterOpusEntity.asExternalModel() = PaterOpus(
    opusName,
    liturgyName
)