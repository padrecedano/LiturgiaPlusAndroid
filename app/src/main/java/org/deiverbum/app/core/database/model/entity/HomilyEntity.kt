package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.SET_DEFAULT
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.traditio.Homily
import org.deiverbum.app.core.model.data.traditio.PaterOpus
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
class HomilyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "homilyID")
    var homiliaId: Int,

    @ColumnInfo(name = "opusFK", defaultValue = "0")
    var obraFK: Int,

    @ColumnInfo(name = "date", defaultValue = "0")
    var fecha: Int,

    @ColumnInfo(name = "book", defaultValue = "0")
    var libro: Int,

    @JvmField
    @ColumnInfo(name = "chapter", defaultValue = "0")
    var capitulo: Int,

    @ColumnInfo(name = "number", defaultValue = "0")
    var numero: Int,

    @ColumnInfo(name = "paragraph", defaultValue = "0")
    var parrafo: Int,

    @ColumnInfo(name = "collectionFK", defaultValue = "0")
    var coleccionFK: Int,

    @ColumnInfo(name = "colNumber", defaultValue = "0")
    var colDoc: Int,

    @ColumnInfo(name = "colParagraph", defaultValue = "0")
    var colParrafo: Int,

    @ColumnInfo(name = "homily")
    var texto: String
)

fun HomilyEntity.asExternalModel(paterOpus: PaterOpus) = Homily(
    texto,
    fecha,
    paterOpus
)

fun HomilyEntity.asExternalModel(paterOpus: PaterOpus, tema: String) = Homily(
    texto,
    fecha,
    paterOpus,
    tema
)