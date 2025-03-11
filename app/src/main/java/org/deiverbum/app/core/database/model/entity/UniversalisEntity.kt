package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.universalis.Universalis
import org.deiverbum.app.util.Constants

/**
 * Entidad que relaciona todos los elementos de la liturgia ligados a una fecha dada.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
@Entity(
    tableName = Constants.UNIVERSALIS_TABLE,
    indices = [Index(
        value = ["todayDate", "timeFK", "liturgyFK", "previousFK", "massReadingFK", "invitatoryFK", "saintFK", "oHymnFK", "oAntiphonFK", "oPsalmFK", "oVerseFK", "oBiblicalFK", "oPatristicFK", "oPrayerFK", "oTeDeum", "lHymnFK", "lAntiphonFK", "lPsalmFK", "lBiblicalFK", "lCanticumFK", "lIntercessionsFK", "lPrayerFK", "tHymnFK", "tAntiphonFK", "tPsalmFK", "tBiblicalFK", "tPrayerFK", "sHymnFK", "sAntiphonFK", "sPsalmFK", "sBiblicalFK", "sPrayerFK", "nHymnFK", "nAntiphonFK", "nPsalmFK", "nBiblicalFK", "nPrayerFK", "vHymnFK", "vAntiphonFK", "vPsalmFK", "vBiblicalFK", "vCanticumFK", "vIntercessionsFK", "vPrayerFK", "nightPrayerFK"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = LiturgyTimeEntity::class,
        parentColumns = arrayOf("timeID"),
        childColumns = arrayOf("timeFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("previousFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = MassReadingJoinEntity::class,
        parentColumns = arrayOf("liturgyFK"),
        childColumns = arrayOf("massReadingFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHInvitatoryJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("invitatoryFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oHymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oPsalmFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oAntiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHOfficeVerseJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oVerseFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oBiblicalFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHOfficePatristicJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oPatristicFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = SaintEntity::class,
        parentColumns = arrayOf("saintID"),
        childColumns = arrayOf("saintFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oPrayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lHymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lPsalmFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lAntiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHGospelCanticleEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lCanticumFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lBiblicalFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHIntercessionsJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lIntercessionsFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lPrayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("tHymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("tPsalmFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("tAntiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("tBiblicalFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("tPrayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("sHymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("sPsalmFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("sAntiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("sBiblicalFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("sPrayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nHymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nPsalmFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nAntiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nBiblicalFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nPrayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vHymnFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vPsalmFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vAntiphonFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHGospelCanticleEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vCanticumFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vBiblicalFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHIntercessionsJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vIntercessionsFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vPrayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHNightPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nightPrayerFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )
    ]
)
//@JsonClass(generateAdapter = true)
data class UniversalisEntity(
    @PrimaryKey
    @ColumnInfo(name = "todayDate")
    var todayDate: Int,

    @ColumnInfo(name = "timeFK", index = true)
    var timeFK: Int,

    @ColumnInfo(name = "liturgyFK", index = true)
    var liturgyFK: Int,

    @ColumnInfo(name = "previousFK", index = true)
    var previousFK: Int,

    @ColumnInfo(name = "hasSaint", defaultValue = "0")
    var hasSaint: Int,

    @ColumnInfo(name = "massReadingFK", index = true)
    var massReadingFK: Int,

    @ColumnInfo(name = "invitatoryFK", index = true)
    var invitatoryFK: Int,

    @ColumnInfo(name = "saintFK", index = true)
    var saintFK: Int,

    @ColumnInfo(name = "oHymnFK", index = true)
    var oHymnFK: Int,

    @ColumnInfo(name = "oAntiphonFK", index = true)
    var oAntiphonFK: Int,

    @ColumnInfo(name = "oPsalmFK", index = true)
    var oPsalmFK: Int,

    @ColumnInfo(name = "oVerseFK", index = true)
    var oVerseFK: Int,

    @ColumnInfo(name = "oBiblicalFK", index = true)
    var oBiblicalFK: Int,

    @ColumnInfo(name = "oPatristicFK", index = true)
    var oPatristicFK: Int,

    @ColumnInfo(name = "oTeDeum", index = true)
    var oTeDeum: Int,

    @ColumnInfo(name = "oPrayerFK", index = true)
    var oPrayerFK: Int,

    @ColumnInfo(name = "lHymnFK", index = true)
    var lHymnFK: Int,

    @ColumnInfo(name = "lAntiphonFK", index = true)
    var lAntiphonFK: Int,

    @ColumnInfo(name = "lPsalmFK", index = true)
    var lPsalmFK: Int,

    @ColumnInfo(name = "lBiblicalFK", index = true)
    var lBiblicalFK: Int,

    @ColumnInfo(name = "lCanticumFK", index = true)
    var lCanticumFK: Int,

    @ColumnInfo(name = "lIntercessionsFK", index = true)
    var lIntercessionsFK: Int,

    @ColumnInfo(name = "lPrayerFK", index = true)
    var lPrayerFK: Int,

    @ColumnInfo(name = "tHymnFK", index = true)
    var tHymnFK: Int,

    @ColumnInfo(name = "tAntiphonFK", index = true)
    var tAntiphonFK: Int,

    @ColumnInfo(name = "tPsalmFK", index = true)
    var tPsalmFK: Int,

    @ColumnInfo(name = "tBiblicalFK", index = true)
    var tBiblicalFK: Int,

    @ColumnInfo(name = "tPrayerFK", index = true)
    var tPrayerFK: Int,

    @ColumnInfo(name = "sHymnFK", index = true)
    var sHymnFK: Int,

    @ColumnInfo(name = "sAntiphonFK", index = true)
    var sAntiphonFK: Int,

    @ColumnInfo(name = "sPsalmFK", index = true)
    var sPsalmFK: Int,

    @ColumnInfo(name = "sBiblicalFK", index = true)
    var sBiblicalFK: Int,

    @ColumnInfo(name = "sPrayerFK", index = true)
    var sPrayerFK: Int,

    @ColumnInfo(name = "nHymnFK", index = true)
    var nHymnFK: Int,

    @ColumnInfo(name = "nAntiphonFK", index = true)
    var nAntiphonFK: Int,

    @ColumnInfo(name = "nPsalmFK", index = true)
    var nPsalmFK: Int,

    @ColumnInfo(name = "nBiblicalFK", index = true)
    var nBiblicalFK: Int,

    @ColumnInfo(name = "nPrayerFK", index = true)
    var nPrayerFK: Int,

    @ColumnInfo(name = "vHymnFK", index = true)
    var vHymnFK: Int,

    @ColumnInfo(name = "vAntiphonFK", index = true)
    var vAntiphonFK: Int,

    @ColumnInfo(name = "vPsalmFK", index = true)
    var vPsalmFK: Int,

    @ColumnInfo(name = "vBiblicalFK", index = true)
    var vBiblicalFK: Int,

    @ColumnInfo(name = "vCanticumFK", index = true)
    var vCanticumFK: Int,

    @ColumnInfo(name = "vIntercessionsFK", index = true)
    var vIntercessionsFK: Int,

    @ColumnInfo(name = "vPrayerFK", index = true)
    var vPrayerFK: Int,

    @ColumnInfo(name = "nightPrayerFK", defaultValue = "71", index = true)
    var nightPrayerFK: Int,
)

/**
 * Retorna un objeto [Universalis] sólo con los datos necesarios para la capa de datos externa.
 *
 * @author A. Cedano
 * @since 2024.1
 */

fun UniversalisEntity.asExternalModel() = Universalis(
    todayDate = todayDate,
    timeFK = timeFK,
    liturgyFK = liturgyFK,
    hasSaint = hasSaint,
    previousFK = previousFK
)