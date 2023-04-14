package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.utils.Constants

/**
 * Entidad que representa a la tabla {@value org.deiverbum.app.utils.Constants#PATER_OPUS}.
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
class PaterOpusEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "opusID")
    var obraId = 0

    @JvmField
    @ColumnInfo(name = "opusName")
    var opusName = ""

    @JvmField
    @ColumnInfo(name = "liturgyName", defaultValue = "")
    var liturgyName = ""

    //@NonNull
    @JvmField
    @ColumnInfo(name = "subTitle", defaultValue = "NULL")
    var subTitle : String? = null

    //@NonNull
    @JvmField
    @ColumnInfo(name = "volume", defaultValue = "NULL")
    var volumen : Int? = null

    @JvmField
    @ColumnInfo(name = "opusDate", defaultValue = "NULL")
    var opusDate : Int? = null

    //@NonNull
    @JvmField
    @ColumnInfo(name = "editorial", defaultValue = "NULL")
    var editorial : String? = null

    //@NonNull
    @JvmField
    @ColumnInfo(name = "city", defaultValue = "NULL")
    var ciudad: String? = null

    //@NonNull
    @JvmField
    @ColumnInfo(name = "opusYear", defaultValue = "NULL")
    var opusYear: Int? = null

    @JvmField
    @ColumnInfo(name = "paterFK", defaultValue = "0", index = true)
    var padreFK = 0

    @JvmField
    @ColumnInfo(name = "typeFK", defaultValue = "0")
    var typeFK = 0

    @JvmField
    @ColumnInfo(name = "collectionFK", defaultValue = "0")
    var collectionFK = 0
}