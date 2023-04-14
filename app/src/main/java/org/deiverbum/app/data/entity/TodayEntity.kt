package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.RESTRICT
import org.deiverbum.app.utils.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.TODAY_TABLE,
    indices = [Index(
        value = ["todayDate", "timeID", "liturgyFK", "previousFK", "massReadingFK", "invitatoryFK", "saintFK", "oHymnFK", "oPsalmodyFK", "oVerseFK", "oBiblicalFK", "oPatristicFK", "oPrayerFK", "oTeDeum", "lHymnFK", "lPsalmodyFK", "lBiblicalFK", "lBenedictusFK", "lIntercessionsFK", "lPrayerFK", "tHymnFK", "tPsalmodyFK", "tBiblicalFK", "tPrayerFK", "sHymnFK", "sPsalmodyFK", "sBiblicalFK", "sPrayerFK", "nHymnFK", "nPsalmodyFK", "nBiblicalFK", "nPrayerFK", "vHymnFK", "vPsalmodyFK", "vBiblicalFK", "vMagnificatFK", "vIntercessionsFK", "vPrayerFK"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("liturgyFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LiturgyEntity::class,
        parentColumns = arrayOf("liturgyID"),
        childColumns = arrayOf("previousFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = MassReadingJoinEntity::class,
        parentColumns = arrayOf("liturgyFK"),
        childColumns = arrayOf("massReadingFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHInvitatoryJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("invitatoryFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oHymnFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oPsalmodyFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHOfficeVerseJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oVerseFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oBiblicalFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHOfficePatristicJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oPatristicFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = SaintEntity::class,
        parentColumns = arrayOf("saintID"),
        childColumns = arrayOf("saintFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("oPrayerFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lHymnFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lPsalmodyFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHGospelCanticleEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lBenedictusFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lBiblicalFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHIntercessionsJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lIntercessionsFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lPrayerFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("tHymnFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("tPsalmodyFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("tBiblicalFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("tPrayerFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("sHymnFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("sPsalmodyFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("sBiblicalFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("sPrayerFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nHymnFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nPsalmodyFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nBiblicalFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("nPrayerFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHHymnJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vHymnFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPsalmodyJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vPsalmodyFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHGospelCanticleEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vMagnificatFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHReadingShortJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vBiblicalFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHIntercessionsJoinEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vIntercessionsFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    ), ForeignKey(
        entity = LHPrayerEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vPrayerFK"),
        onDelete = RESTRICT,
        onUpdate = RESTRICT
    )]
)
class TodayEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "todayDate")
    var hoy = 0

    @JvmField
    @ColumnInfo(name = "timeID", defaultValue = "1", index = true)
    var tiempoId = 1

    @JvmField
    @ColumnInfo(name = "weekDay", defaultValue = "1", index = true)
    var weekDay = 1

    @JvmField
    @ColumnInfo(name = "liturgyFK", defaultValue = "1", index = true)
    var liturgyFK = 1

    @JvmField
    @ColumnInfo(name = "previousFK", defaultValue = "1", index = true)
    var previoId = 1

    @JvmField
    @ColumnInfo(name = "hasSaint", defaultValue = "0")
    var hasSaint = 0

    @JvmField
    @ColumnInfo(name = "massReadingFK", index = true)
    var mLecturasFK = 0

    @JvmField
    @ColumnInfo(name = "invitatoryFK", index = true)
    var invitatorioFK = 0

    @JvmField
    @ColumnInfo(name = "saintFK", index = true)
    var santoFK = 1

    @JvmField
    @ColumnInfo(name = "oHymnFK", index = true)
    var oHimnoFK = 0

    @JvmField
    @ColumnInfo(name = "oPsalmodyFK", index = true)
    var oSalmodiaFK = 0

    @JvmField
    @ColumnInfo(name = "oVerseFK", index = true)
    var oVersoFK = 0

    @JvmField
    @ColumnInfo(name = "oBiblicalFK", index = true)
    var oBiblicaFK = 0

    @JvmField
    @ColumnInfo(name = "oPatristicFK", index = true)
    var oPatristicaFK = 0

    @JvmField
    @ColumnInfo(name = "oTeDeum", defaultValue = "0")
    var oTeDeum = 0

    @JvmField
    @ColumnInfo(name = "oPrayerFK", index = true)
    var oOracionFK = 0

    @JvmField
    @ColumnInfo(name = "lHymnFK", index = true)
    var lHimnoFK = 0

    @JvmField
    @ColumnInfo(name = "lPsalmodyFK", index = true)
    var lSalmodiaFK = 0

    @JvmField
    @ColumnInfo(name = "lBiblicalFK", index = true)
    var lBiblicaFK = 0

    @JvmField
    @ColumnInfo(name = "lBenedictusFK", index = true)
    var lBenedictusFK = 0

    @JvmField
    @ColumnInfo(name = "lIntercessionsFK", index = true)
    var lPrecesFK = 0

    @JvmField
    @ColumnInfo(name = "lPrayerFK", index = true)
    var lOracionFK = 0

    @JvmField
    @ColumnInfo(name = "tHymnFK", index = true)
    var tHimnoFK = 0

    @JvmField
    @ColumnInfo(name = "tPsalmodyFK", index = true)
    var tSalmodiaFK = 0

    @JvmField
    @ColumnInfo(name = "tBiblicalFK", index = true)
    var tBiblicaFK = 0

    @JvmField
    @ColumnInfo(name = "tPrayerFK", index = true)
    var tOracionFK = 0

    @JvmField
    @ColumnInfo(name = "sHymnFK", index = true)
    var sHimnoFK = 0

    @JvmField
    @ColumnInfo(name = "sPsalmodyFK", index = true)
    var sSalmodiaFK = 0

    @JvmField
    @ColumnInfo(name = "sBiblicalFK", index = true)
    var sBiblicaFK = 0

    @JvmField
    @ColumnInfo(name = "sPrayerFK", index = true)
    var sOracionFK = 0

    @JvmField
    @ColumnInfo(name = "nHymnFK", index = true)
    var nHimnoFK = 0

    @JvmField
    @ColumnInfo(name = "nPsalmodyFK", index = true)
    var nSalmodiaFK = 0

    @JvmField
    @ColumnInfo(name = "nBiblicalFK", index = true)
    var nBiblicaFK = 0

    @JvmField
    @ColumnInfo(name = "nPrayerFK", index = true)
    var nOracionFK = 0

    @JvmField
    @ColumnInfo(name = "vHymnFK", index = true)
    var vHimnoFK = 0

    @JvmField
    @ColumnInfo(name = "vPsalmodyFK", index = true)
    var vSalmodiaFK = 0

    @JvmField
    @ColumnInfo(name = "vBiblicalFK", index = true)
    var vBiblicaFK = 0

    @JvmField
    @ColumnInfo(name = "vMagnificatFK", index = true)
    var vMagnificatFK = 0

    @JvmField
    @ColumnInfo(name = "vIntercessionsFK", index = true)
    var vPrecesFK = 0

    @JvmField
    @ColumnInfo(name = "vPrayerFK", index = true)
    var vOracionFK = 0
    val teDeum: Boolean
        get() = oTeDeum == 1
}