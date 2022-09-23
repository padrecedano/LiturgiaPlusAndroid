package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "today",
        foreignKeys =
        {
                @ForeignKey(
                        entity = LiturgyEntity.class,
                        parentColumns = "liturgyID",
                        childColumns = "liturgyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(
                        entity = LiturgyEntity.class,
                        parentColumns = "liturgyID",
                        childColumns = "previousFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                /*@ForeignKey(
                        entity = MassReadingEntity.class,
                        parentColumns = "liturgyFK",
                        childColumns = "massReadingFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),*/

                @ForeignKey(
                        entity = LHInvitatoryJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "invitatoryFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oHymnFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oPsalmodyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOficceVerseJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oVerseFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOfficeBiblicalJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oBiblicalFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOfficePatristicJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oPatristicFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = SaintEntity.class,
                        parentColumns = "saintID",
                        childColumns = "saintFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oPrayerFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lHymnFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lPsalmodyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHGospelCanticleEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lBenedictusFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lBiblicalFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHIntercessionsJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lIntercessionsFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lPrayerFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),


                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tHymnFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tPsalmodyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tBiblicalFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tPrayerFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sHymnFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sPsalmodyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sBiblicalFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sPrayerFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),


                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nHymnFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nPsalmodyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nBiblicalFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nPrayerFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(
                        entity = LHHymnJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vHymnFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPsalmodyJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vPsalmodyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHGospelCanticleEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vMagnificatFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHReadingShortJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vBiblicalFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHIntercessionsJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vIntercessionsFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPrayerEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vPrayerFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)

        })
public class TodayEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "todayDate")
    public Integer hoy=0;

    @NonNull
    @ColumnInfo(name = "timeID")
    public Integer tiempoId=0;

    @NonNull
    @ColumnInfo(name = "liturgyFK")
    public Integer liturgyFK=0;

    @NonNull
    @ColumnInfo(name = "previousFK")
    public Integer previoId=0;

    @NonNull
    @ColumnInfo(name = "weekDayFK")
    public Integer feriaId=0;

    @NonNull
    @ColumnInfo(name = "massReadingFK")
    public Integer mLecturasFK=0;

    /*
    @ColumnInfo(name = "version")
    public Integer version;
*/
    @NonNull
    @ColumnInfo(name = "invitatoryFK")
    public Integer invitatorioFK=0;

    @NonNull
    @ColumnInfo(name = "saintFK")
    public Integer santoFK;

    @NonNull
    @ColumnInfo(name = "oHymnFK")
    public Integer oHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "oPsalmodyFK")
    public Integer oSalmodiaFK=0;

    /*
    @NonNull
    @ColumnInfo(name = "oficioFK")
    public Integer oficioFK=0;
*/
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
/*
    public void setVersion(Integer version) {
        this.version = version;
    }
    public Integer getVersion() {
        return version;
    }
*/
    @NonNull
    public Integer getHoy() {
        return hoy;
    }
    public void setHoy(@NonNull Integer hoy) {
        this.hoy = hoy;
    }


    public Integer getPrevioId() {
        return previoId;
    }

    public void setPrevioId(Integer previoId) {
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

