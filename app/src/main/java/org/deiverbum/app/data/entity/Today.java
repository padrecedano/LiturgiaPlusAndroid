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
                        entity = LiturgiaEntity.class,
                        parentColumns = "liturgyID",
                        childColumns = "weekDayFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(
                        entity = LiturgyGroupEntity.class,
                        parentColumns = "groupID",
                        childColumns = "massReadingFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(
                        entity = LHInvitatorioJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "invitatoryFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHHimnoJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oHymnFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHSalmodiaJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oPsalmodyFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOficioVersoJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oVerseFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHBiblicaOficioJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oBiblicalFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPatristicaOficioJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oPatristicFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = SantoEntity.class,
                        parentColumns = "saintID",
                        childColumns = "saintFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOracionEntity.class,
                        parentColumns = "groupID",
                        childColumns = "oPrayerFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHHimnoJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lHymnFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHSalmodiaJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lPsalmodyFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHCanticoEvangelicoEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lBenedictusFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHBiblicaBreveJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lBiblicalFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPrecesJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lIntercessionsFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOracionEntity.class,
                        parentColumns = "groupID",
                        childColumns = "lPrayerFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),


                @ForeignKey(
                        entity = LHHimnoJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tHymnFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHSalmodiaJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tPsalmodyFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHBiblicaBreveJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tBiblicalFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOracionEntity.class,
                        parentColumns = "groupID",
                        childColumns = "tPrayerFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(
                        entity = LHHimnoJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sHymnFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHSalmodiaJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sPsalmodyFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHBiblicaBreveJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sBiblicalFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOracionEntity.class,
                        parentColumns = "groupID",
                        childColumns = "sPrayerFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),


                @ForeignKey(
                        entity = LHHimnoJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nHymnFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHSalmodiaJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nPsalmodyFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHBiblicaBreveJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nBiblicalFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOracionEntity.class,
                        parentColumns = "groupID",
                        childColumns = "nPrayerFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(
                        entity = LHHimnoJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vHymnFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHSalmodiaJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vPsalmodyFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHCanticoEvangelicoEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vMagnificatFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHBiblicaBreveJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vBiblicalFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPrecesJoinEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vIntercessionsFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOracionEntity.class,
                        parentColumns = "groupID",
                        childColumns = "vPrayerFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE)


        })
public class Today {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "todayDate")
    public Integer hoy=0;

    @NonNull
    @ColumnInfo(name = "weekDayFK", defaultValue= "0")
    public Integer feriaId=0;

    @ColumnInfo(name = "massReadingFK", defaultValue= "0")
    public Integer mLecturasFK;

    @ColumnInfo(name = "previousFK")
    public Integer previoId;

    @NonNull
    @ColumnInfo(name = "timeID")
    public Integer tiempoId=0;

    @ColumnInfo(name = "version", defaultValue= "0")
    public Integer version;

    @NonNull
    @ColumnInfo(name = "invitatoryFK", defaultValue= "0")
    public Integer invitatorioFK=0;

    @NonNull
    @ColumnInfo(name = "oHymnFK", defaultValue= "0")
    public Integer oHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "oPsalmodyFK", defaultValue= "0")
    public Integer oSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "oficioFK", defaultValue= "0")
    public Integer oficioFK=0;

    @NonNull
    @ColumnInfo(name = "oVerseFK", defaultValue= "0")
    public Integer oVersoFK=0;

    @NonNull
    @ColumnInfo(name = "oBiblicalFK", defaultValue= "0")
    public Integer oBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "oPatristicFK", defaultValue= "0")
    public Integer oPatristicaFK=0;

    @ColumnInfo(name = "saintFK", defaultValue= "0")
    public Integer santoFK;

    @NonNull
    @ColumnInfo(name = "oPrayerFK", defaultValue= "0")
    public Integer oOracionFK=0;

    @NonNull
    @ColumnInfo(name = "oTeDeum", defaultValue= "0")
    public Integer oTeDeum=0;

    @NonNull
    @ColumnInfo(name = "lHymnFK", defaultValue= "0")
    public Integer lHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "lPsalmodyFK", defaultValue= "0")
    public Integer lSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "lBiblicalFK", defaultValue= "0")
    public Integer lBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "lBenedictusFK", defaultValue= "0")
    public Integer lBenedictusFK=0;

    @NonNull
    @ColumnInfo(name = "lIntercessionsFK", defaultValue= "0")
    public Integer lPrecesFK=0;

    @NonNull
    @ColumnInfo(name = "lPrayerFK", defaultValue= "0")
    public Integer lOracionFK=0;


    @NonNull
    @ColumnInfo(name = "tHymnFK", defaultValue= "0")
    public Integer tHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "tPsalmodyFK", defaultValue= "0")
    public Integer tSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "tBiblicalFK", defaultValue= "0")
    public Integer tBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "tPrayerFK", defaultValue= "0")
    public Integer tOracionFK=0;

    @NonNull
    @ColumnInfo(name = "sHymnFK", defaultValue= "0")
    public Integer sHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "sPsalmodyFK", defaultValue= "0")
    public Integer sSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "sBiblicalFK", defaultValue= "0")
    public Integer sBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "sPrayerFK", defaultValue= "0")
    public Integer sOracionFK=0;

    @NonNull
    @ColumnInfo(name = "nHymnFK", defaultValue= "0")
    public Integer nHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "nPsalmodyFK", defaultValue= "0")
    public Integer nSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "nBiblicalFK", defaultValue= "0")
    public Integer nBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "nPrayerFK", defaultValue= "0")
    public Integer nOracionFK=0;


    @NonNull
    @ColumnInfo(name = "vHymnFK", defaultValue= "0")
    public Integer vHimnoFK=0;

    @NonNull
    @ColumnInfo(name = "vPsalmodyFK", defaultValue= "0")
    public Integer vSalmodiaFK=0;

    @NonNull
    @ColumnInfo(name = "vBiblicalFK", defaultValue= "0")
    public Integer vBiblicaFK=0;

    @NonNull
    @ColumnInfo(name = "vMagnificatFK", defaultValue= "0")
    public Integer vMagnificatFK=0;

    @NonNull
    @ColumnInfo(name = "vIntercessionsFK", defaultValue= "0")
    public Integer vPrecesFK=0;

    @NonNull
    @ColumnInfo(name = "vPrayerFK", defaultValue= "0")
    public Integer vOracionFK=0;

    public void setVersion(Integer version) {
        this.version = version;
    }
    public Integer getVersion() {
        return version;
    }

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

