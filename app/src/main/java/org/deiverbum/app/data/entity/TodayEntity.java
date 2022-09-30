package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.TODAY_TABLE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = TODAY_TABLE,
        indices = {@Index(value = {"todayDate","timeID","liturgyFK","previousFK","massReadingFK","invitatoryFK","saintFK","oHymnFK","oPsalmodyFK","oVerseFK","oBiblicalFK","oPatristicFK","oPrayerFK","oTeDeum","lHymnFK","lPsalmodyFK","lBiblicalFK","lBenedictusFK","lIntercessionsFK","lPrayerFK","tHymnFK","tPsalmodyFK","tBiblicalFK","tPrayerFK","sHymnFK","sPsalmodyFK","sBiblicalFK","sPrayerFK","nHymnFK","nPsalmodyFK","nBiblicalFK","nPrayerFK","vHymnFK","vPsalmodyFK","vBiblicalFK","vMagnificatFK","vIntercessionsFK","vPrayerFK"},unique = true)},

        foreignKeys =
        {
                @ForeignKey(
                        entity = LiturgyEntity.class,
                        parentColumns = "liturgyID",
                        childColumns = "liturgyFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),

                @ForeignKey(
                        entity = LiturgyEntity.class,
                        parentColumns = "liturgyID",
                        childColumns = "previousFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = MassReadingJoinEntity.class,
                        parentColumns = "liturgyFK",
                        childColumns = "massReadingFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHInvitatoryJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "invitatoryFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oHymnFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oPsalmodyFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHOficceVerseJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oVerseFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHOfficeBiblicalJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oBiblicalFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHOfficePatristicJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oPatristicFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = SaintEntity.class,
                        parentColumns = "saintID",
                        childColumns = "saintFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oPrayerFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lHymnFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lPsalmodyFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHGospelCanticleJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lBenedictusFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lBiblicalFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHIntercessionsJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lIntercessionsFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lPrayerFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tHymnFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tPsalmodyFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tBiblicalFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tPrayerFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sHymnFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sPsalmodyFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sBiblicalFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sPrayerFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nHymnFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nPsalmodyFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nBiblicalFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nPrayerFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vHymnFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vPsalmodyFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHGospelCanticleJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vMagnificatFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vBiblicalFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHIntercessionsJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vIntercessionsFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vPrayerFK",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT)
        })
public class TodayEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "todayDate")
    public Integer hoy=0;

    @NonNull
    @ColumnInfo(name = "timeID", defaultValue = "1")
    public Integer tiempoId=1;

    @NonNull
    @ColumnInfo(name = "weekDay", defaultValue = "1")
    public Integer weekDay=1;

    @NonNull
    @ColumnInfo(name = "liturgyFK", defaultValue = "1")
    public Integer liturgyFK=1;

    @NonNull
    @ColumnInfo(name = "previousFK", defaultValue = "1")
    public Integer previoId=1;

    @NonNull
    @ColumnInfo(name = "hasSaint", defaultValue= "0")
    public Integer hasSaint=0;

    @NonNull
    @ColumnInfo(name = "massReadingFK")
    public Integer mLecturasFK=0;
    
    @NonNull
    @ColumnInfo(name = "invitatoryFK")
    public Integer invitatorioFK=0;

    @NonNull
    @ColumnInfo(name = "saintFK")
    public Integer santoFK=1;

    @NonNull
    @ColumnInfo(name = "oHymnFK")
    public Integer oHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "oPsalmodyFK")
    public Integer oSalmodiaFK=0;
    
    @NonNull
    @ColumnInfo(name = "oVerseFK")
    public Integer oVersoFK=0;

    @NonNull
    @ColumnInfo(name = "oBiblicalFK")
    public Integer oBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "oPatristicFK")
    public Integer oPatristicaFK=0;

    @NonNull
    @ColumnInfo(name = "oTeDeum", defaultValue= "0")
    public Integer oTeDeum=0;

    @NonNull
    @ColumnInfo(name = "oPrayerFK")
    public Integer oOracionFK=0;

    @NonNull
    @ColumnInfo(name = "lHymnFK")
    public Integer lHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "lPsalmodyFK")
    public Integer lSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "lBiblicalFK")
    public Integer lBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "lBenedictusFK")
    public Integer lBenedictusFK=0;

    @NonNull
    @ColumnInfo(name = "lIntercessionsFK")
    public Integer lPrecesFK=0;

    @NonNull
    @ColumnInfo(name = "lPrayerFK")
    public Integer lOracionFK=0;


    @NonNull
    @ColumnInfo(name = "tHymnFK")
    public Integer tHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "tPsalmodyFK")
    public Integer tSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "tBiblicalFK")
    public Integer tBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "tPrayerFK")
    public Integer tOracionFK=0;

    @NonNull
    @ColumnInfo(name = "sHymnFK")
    public Integer sHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "sPsalmodyFK")
    public Integer sSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "sBiblicalFK")
    public Integer sBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "sPrayerFK")
    public Integer sOracionFK=0;

    @NonNull
    @ColumnInfo(name = "nHymnFK")
    public Integer nHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "nPsalmodyFK")
    public Integer nSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "nBiblicalFK")
    public Integer nBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "nPrayerFK")
    public Integer nOracionFK=0;


    @NonNull
    @ColumnInfo(name = "vHymnFK")
    public Integer vHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "vPsalmodyFK")
    public Integer vSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "vBiblicalFK")
    public Integer vBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "vMagnificatFK")
    public Integer vMagnificatFK=0;

    @NonNull
    @ColumnInfo(name = "vIntercessionsFK")
    public Integer vPrecesFK=0;

    @NonNull
    @ColumnInfo(name = "vPrayerFK")
    public Integer vOracionFK=0;

    @NonNull
    public Integer getHoy() {
        return hoy;
    }
    public void setHoy(@NonNull Integer hoy) {
        this.hoy = hoy;
    }

    @NonNull
    public Integer getPrevioId() {
        return previoId;
    }

    public void setPrevioId(@NonNull Integer previoId) {
        this.previoId = previoId;
    }

    @NonNull
    public Integer getTiempoId() {
        return tiempoId;
    }

    @SuppressWarnings("unused")
    public boolean getTeDeum() {
        return oTeDeum == 1;
    }
}

