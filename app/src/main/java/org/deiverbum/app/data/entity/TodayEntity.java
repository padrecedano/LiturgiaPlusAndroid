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
 * @since 2023.1
 */

@Entity(tableName = TODAY_TABLE,
        indices = {@Index(value = {"todayDate", "timeID", "liturgyFK", "previousFK", "massReadingFK", "invitatoryFK", "saintFK", "oHymnFK", "oPsalmodyFK", "oVerseFK", "oBiblicalFK", "oPatristicFK", "oPrayerFK", "oTeDeum", "lHymnFK", "lPsalmodyFK", "lBiblicalFK", "lBenedictusFK", "lIntercessionsFK", "lPrayerFK", "tHymnFK", "tPsalmodyFK", "tBiblicalFK", "tPrayerFK", "sHymnFK", "sPsalmodyFK", "sBiblicalFK", "sPrayerFK", "nHymnFK", "nPsalmodyFK", "nBiblicalFK", "nPrayerFK", "vHymnFK", "vPsalmodyFK", "vBiblicalFK", "vMagnificatFK", "vIntercessionsFK", "vPrayerFK"}, unique = true)},

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
                                entity = LHOfficeVerseJoinEntity.class,
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
                                entity = LHGospelCanticleEntity.class,
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
                                entity = LHGospelCanticleEntity.class,
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
    public Integer hoy = 0;

    @NonNull
    @ColumnInfo(name = "timeID", defaultValue = "1", index = true)
    public Integer tiempoId = 1;

    @NonNull
    @ColumnInfo(name = "weekDay", defaultValue = "1", index = true)
    public Integer weekDay = 1;

    @NonNull
    @ColumnInfo(name = "liturgyFK", defaultValue = "1", index = true)
    public Integer liturgyFK = 1;

    @NonNull
    @ColumnInfo(name = "previousFK", defaultValue = "1", index = true)
    public Integer previoId = 1;

    @NonNull
    @ColumnInfo(name = "hasSaint", defaultValue = "0")
    public Integer hasSaint = 0;

    @NonNull
    @ColumnInfo(name = "massReadingFK", index = true)
    public Integer mLecturasFK = 0;

    @NonNull
    @ColumnInfo(name = "invitatoryFK", index = true)
    public Integer invitatorioFK = 0;

    @NonNull
    @ColumnInfo(name = "saintFK", index = true)
    public Integer santoFK = 1;

    @NonNull
    @ColumnInfo(name = "oHymnFK", index = true)
    public Integer oHimnoFK = 0;

    @NonNull
    @ColumnInfo(name = "oPsalmodyFK", index = true)
    public Integer oSalmodiaFK = 0;

    @NonNull
    @ColumnInfo(name = "oVerseFK", index = true)
    public Integer oVersoFK = 0;

    @NonNull
    @ColumnInfo(name = "oBiblicalFK", index = true)
    public Integer oBiblicaFK = 0;

    @NonNull
    @ColumnInfo(name = "oPatristicFK", index = true)
    public Integer oPatristicaFK = 0;

    @NonNull
    @ColumnInfo(name = "oTeDeum", defaultValue = "0")
    public Integer oTeDeum = 0;

    @NonNull
    @ColumnInfo(name = "oPrayerFK", index = true)
    public Integer oOracionFK = 0;

    @NonNull
    @ColumnInfo(name = "lHymnFK", index = true)
    public Integer lHimnoFK = 0;

    @NonNull
    @ColumnInfo(name = "lPsalmodyFK", index = true)
    public Integer lSalmodiaFK = 0;

    @NonNull
    @ColumnInfo(name = "lBiblicalFK", index = true)
    public Integer lBiblicaFK = 0;

    @NonNull
    @ColumnInfo(name = "lBenedictusFK", index = true)
    public Integer lBenedictusFK = 0;

    @NonNull
    @ColumnInfo(name = "lIntercessionsFK", index = true)
    public Integer lPrecesFK = 0;

    @NonNull
    @ColumnInfo(name = "lPrayerFK", index = true)
    public Integer lOracionFK = 0;


    @NonNull
    @ColumnInfo(name = "tHymnFK", index = true)
    public Integer tHimnoFK = 0;

    @NonNull
    @ColumnInfo(name = "tPsalmodyFK", index = true)
    public Integer tSalmodiaFK = 0;

    @NonNull
    @ColumnInfo(name = "tBiblicalFK", index = true)
    public Integer tBiblicaFK = 0;

    @NonNull
    @ColumnInfo(name = "tPrayerFK", index = true)
    public Integer tOracionFK = 0;

    @NonNull
    @ColumnInfo(name = "sHymnFK", index = true)
    public Integer sHimnoFK = 0;

    @NonNull
    @ColumnInfo(name = "sPsalmodyFK", index = true)
    public Integer sSalmodiaFK = 0;

    @NonNull
    @ColumnInfo(name = "sBiblicalFK", index = true)
    public Integer sBiblicaFK = 0;

    @NonNull
    @ColumnInfo(name = "sPrayerFK", index = true)
    public Integer sOracionFK = 0;

    @NonNull
    @ColumnInfo(name = "nHymnFK", index = true)
    public Integer nHimnoFK = 0;

    @NonNull
    @ColumnInfo(name = "nPsalmodyFK", index = true)
    public Integer nSalmodiaFK = 0;

    @NonNull
    @ColumnInfo(name = "nBiblicalFK", index = true)
    public Integer nBiblicaFK = 0;

    @NonNull
    @ColumnInfo(name = "nPrayerFK", index = true)
    public Integer nOracionFK = 0;


    @NonNull
    @ColumnInfo(name = "vHymnFK", index = true)
    public Integer vHimnoFK = 0;

    @NonNull
    @ColumnInfo(name = "vPsalmodyFK", index = true)
    public Integer vSalmodiaFK = 0;

    @NonNull
    @ColumnInfo(name = "vBiblicalFK", index = true)
    public Integer vBiblicaFK = 0;

    @NonNull
    @ColumnInfo(name = "vMagnificatFK", index = true)
    public Integer vMagnificatFK = 0;

    @NonNull
    @ColumnInfo(name = "vIntercessionsFK", index = true)
    public Integer vPrecesFK = 0;

    @NonNull
    @ColumnInfo(name = "vPrayerFK", index = true)
    public Integer vOracionFK = 0;

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

