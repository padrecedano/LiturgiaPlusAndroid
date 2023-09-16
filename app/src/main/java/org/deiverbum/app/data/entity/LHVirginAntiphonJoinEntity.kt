package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * Entidad para la tabla **`lh_virgin_antiphon_join`** de la base de datos, que se ocupa de gestionar la relación asociativa de las antífonas de la Virgen en las Completas.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.LH_VIRGIN_ANTIPHON_JOIN,
    indices = [Index(value = ["groupID", "antiphonFK"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = VirginAntiphonEntity::class,
        parentColumns = arrayOf("antiphonID"),
        childColumns = arrayOf("antiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class LHVirginAntiphonJoinEntity (
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    val groupID:Int,

    @ColumnInfo(name = "antiphonFK", index = true)
    val antiphonFK:Int,
)