package org.deiverbum.app.data.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.TODAY_TABLE,
    indices = [Index(
        value = ["todayDate", "timeID", "liturgyFK", "previousFK", "massReadingFK", "invitatoryFK", "saintFK", "oHymnFK", "oPsalmodyFK", "oVerseFK", "oBiblicalFK", "oPatristicFK", "oPrayerFK", "oTeDeum", "lHymnFK", "lPsalmodyFK", "lBiblicalFK", "lBenedictusFK", "lIntercessionsFK", "lPrayerFK", "tHymnFK", "tPsalmodyFK", "tBiblicalFK", "tPrayerFK", "sHymnFK", "sPsalmodyFK", "sBiblicalFK", "sPrayerFK", "nHymnFK", "nPsalmodyFK", "nBiblicalFK", "nPrayerFK", "vHymnFK", "vPsalmodyFK", "vBiblicalFK", "vMagnificatFK", "vIntercessionsFK", "vPrayerFK", "nightPrayerFK"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
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
        childColumns = arrayOf("oPsalmodyFK"),
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
        childColumns = arrayOf("lPsalmodyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHGospelCanticleEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("lBenedictusFK"),
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
        childColumns = arrayOf("tPsalmodyFK"),
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
        childColumns = arrayOf("sPsalmodyFK"),
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
        childColumns = arrayOf("nPsalmodyFK"),
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
        childColumns = arrayOf("vPsalmodyFK"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    ), ForeignKey(
        entity = LHGospelCanticleEntity::class,
        parentColumns = arrayOf("groupID"),
        childColumns = arrayOf("vMagnificatFK"),
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
class TodayEntity {
    @JvmField
    @PrimaryKey
    @ColumnInfo(name = "todayDate")
    var hoy = 0

    @JvmField
    @ColumnInfo(name = "timeID", index = true)
    var tiempoId = 1

    @JvmField
    @ColumnInfo(name = "weekDay", defaultValue = "1", index = true)
    var weekDay = 1

    @JvmField
    @ColumnInfo(name = "liturgyFK", index = true)
    var liturgyFK = 1

    @JvmField
    @ColumnInfo(name = "previousFK", index = true)
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
    @ColumnInfo(name = "oTeDeum", index = true)
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

    @JvmField
    @ColumnInfo(name = "nightPrayerFK", defaultValue = "71", index = true)
    var nightPrayerFK = 71

    /*val teDeum: Boolean
        get() = oTeDeum == 1*/
}