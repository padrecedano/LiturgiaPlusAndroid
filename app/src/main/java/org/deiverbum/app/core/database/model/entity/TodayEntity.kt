package org.deiverbum.app.core.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Entity(
    tableName = Constants.TODAY_TABLE,
    indices = [Index(
        value = ["todayDate", "timeFK", "liturgyFK", "previousFK", "massReadingFK", "invitatoryFK", "saintFK", "oHymnFK", "oPsalmodyFK", "oVerseFK", "oBiblicalFK", "oPatristicFK", "oPrayerFK", "oTeDeum", "lHymnFK", "lPsalmodyFK", "lBiblicalFK", "lBenedictusFK", "lIntercessionsFK", "lPrayerFK", "tHymnFK", "tPsalmodyFK", "tBiblicalFK", "tPrayerFK", "sHymnFK", "sPsalmodyFK", "sBiblicalFK", "sPrayerFK", "nHymnFK", "nPsalmodyFK", "nBiblicalFK", "nPrayerFK", "vHymnFK", "vPsalmodyFK", "vBiblicalFK", "vMagnificatFK", "vIntercessionsFK", "vPrayerFK", "nightPrayerFK"],
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
data class TodayEntity(
    @PrimaryKey
    @ColumnInfo(name = "todayDate")
    var todayDate: Int,

    @ColumnInfo(name = "timeFK", index = true)
    var timeFK: Int,
    /*
        @ColumnInfo(name = "weekDay", defaultValue = "1", index = true)
        var weekDay: Int,
    */
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

    @ColumnInfo(name = "oPsalmodyFK", index = true)
    var oPsalmodyFK: Int,

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

    @ColumnInfo(name = "lPsalmodyFK", index = true)
    var lPsalmodyFK: Int,

    @ColumnInfo(name = "lBiblicalFK", index = true)
    var lBiblicalFK: Int,

    @ColumnInfo(name = "lBenedictusFK", index = true)
    var lBenedictusFK: Int,

    @ColumnInfo(name = "lIntercessionsFK", index = true)
    var lIntercessionsFK: Int,

    @ColumnInfo(name = "lPrayerFK", index = true)
    var lPrayerFK: Int,

    @ColumnInfo(name = "tHymnFK", index = true)
    var tHymnFK: Int,

    @ColumnInfo(name = "tPsalmodyFK", index = true)
    var tPsalmodyFK: Int,

    @ColumnInfo(name = "tBiblicalFK", index = true)
    var tBiblicalFK: Int,

    @ColumnInfo(name = "tPrayerFK", index = true)
    var tPrayerFK: Int,

    @ColumnInfo(name = "sHymnFK", index = true)
    var sHymnFK: Int,

    @ColumnInfo(name = "sPsalmodyFK", index = true)
    var sPsalmodyFK: Int,

    @ColumnInfo(name = "sBiblicalFK", index = true)
    var sBiblicalFK: Int,

    @ColumnInfo(name = "sPrayerFK", index = true)
    var sPrayerFK: Int,

    @ColumnInfo(name = "nHymnFK", index = true)
    var nHymnFK: Int,

    @ColumnInfo(name = "nPsalmodyFK", index = true)
    var nPsalmodyFK: Int,

    @ColumnInfo(name = "nBiblicalFK", index = true)
    var nBiblicalFK: Int,

    @ColumnInfo(name = "nPrayerFK", index = true)
    var nPrayerFK: Int,

    @ColumnInfo(name = "vHymnFK", index = true)
    var vHymnFK: Int,

    @ColumnInfo(name = "vPsalmodyFK", index = true)
    var vPsalmodyFK: Int,

    @ColumnInfo(name = "vBiblicalFK", index = true)
    var vBiblicalFK: Int,

    @ColumnInfo(name = "vMagnificatFK", index = true)
    var vMagnificatFK: Int,

    @ColumnInfo(name = "vIntercessionsFK", index = true)
    var vIntercessionsFK: Int,

    @ColumnInfo(name = "vPrayerFK", index = true)
    var vPrayerFK: Int,

    @ColumnInfo(name = "nightPrayerFK", defaultValue = "71", index = true)
    var nightPrayerFK: Int,

    //@Embedded var address: LiturgyEntity?

)

/*var teDeum: Boolean
    get() = oTeDeum == 1*/
