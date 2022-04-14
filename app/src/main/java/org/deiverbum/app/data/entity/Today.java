package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Transaction;

import java.sql.Date;
import java.util.List;

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
                        parentColumns = "liturgiaId",
                        childColumns = "feriaFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
/*
                @ForeignKey(
                        entity = MisaLecturaEntity.class,
                        parentColumns = "liturgiaId",
                        childColumns = "mLecturasFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),*/

                @ForeignKey(
                        entity = InvitatorioEntity.class,
                        parentColumns = "invitatorioId",
                        childColumns = "invitatorioFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = HimnoEntity.class,
                        parentColumns = "himnoId",
                        childColumns = "oHimnoFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                /*@ForeignKey(
                        entity = SalmodiaEntity.class,
                        parentColumns = "liturgiaId",
                        childColumns = "oSalmodiaFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),*/
                @ForeignKey(
                        entity = LHOficioResponsorioEntity.class,
                        parentColumns = "responsorioId",
                        childColumns = "oResponsorioFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHBiblicaEntity.class,
                        parentColumns = "biblicaId",
                        childColumns = "oBiblicaFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPatristicaEntity.class,
                        parentColumns = "patristicaId",
                        childColumns = "oPatristicaFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = SantoEntity.class,
                        parentColumns = "santoId",
                        childColumns = "santoFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOracionEntity.class,
                        parentColumns = "liturgiaId",
                        childColumns = "oOracionFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = HimnoEntity.class,
                        parentColumns = "himnoId",
                        childColumns = "lHimnoFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHCanticoEvangelicoEntity.class,
                        parentColumns = "grupoId",
                        childColumns = "lBenedictusFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHBiblicaEntity.class,
                        parentColumns = "biblicaId",
                        childColumns = "lBiblicaFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHPrecesJoinEntity.class,
                        parentColumns = "grupoId",
                        childColumns = "lPrecesFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHOracionEntity.class,
                        parentColumns = "liturgiaId",
                        childColumns = "lOracionFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),


                @ForeignKey(
                        entity = HimnoEntity.class,
                        parentColumns = "himnoId",
                        childColumns = "tHimnoFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(
                        entity = LHBiblicaEntity.class,
                        parentColumns = "biblicaId",
                        childColumns = "tBiblicaFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),

                @ForeignKey(
                        entity = LHOracionEntity.class,
                        parentColumns = "liturgiaId",
                        childColumns = "tOracionFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE),

        })
public class Today {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "hoy")
    public Integer hoy;

    @NonNull
    @ColumnInfo(name = "feriaFK", defaultValue= "0")
    public Integer feriaId;

    @ColumnInfo(name = "mLecturasFK", defaultValue= "0")
    public Integer mLecturasFK;

    @ColumnInfo(name = "previoId")
    public Integer previoId;

    @NonNull
    @ColumnInfo(name = "tiempoId")
    public Integer tiempoId;

    @ColumnInfo(name = "version", defaultValue= "0")
    public Integer version;

    @NonNull
    @ColumnInfo(name = "invitatorioFK", defaultValue= "0")
    public Integer invitatorioFK;

    @NonNull
    @ColumnInfo(name = "oHimnoFK", defaultValue= "0")
    public Integer oHimnoFK;

    @NonNull
    @ColumnInfo(name = "oSalmodiaFK", defaultValue= "0")
    public Integer oSalmodiaFK;

    @NonNull
    @ColumnInfo(name = "oficioFK", defaultValue= "0")
    public Integer oficioFK;

    @NonNull
    @ColumnInfo(name = "oResponsorioFK", defaultValue= "0")
    public Integer oResponsorioFK;

    @NonNull
    @ColumnInfo(name = "oBiblicaFK", defaultValue= "0")
    public Integer oBiblicaFK;

    @NonNull
    @ColumnInfo(name = "oPatristicaFK", defaultValue= "0")
    public Integer oPatristicaFK;

    @ColumnInfo(name = "santoFK", defaultValue= "0")
    public Integer santoFK;

    @NonNull
    @ColumnInfo(name = "oOracionFK", defaultValue= "0")
    public Integer oOracionFK;

    @NonNull
    @ColumnInfo(name = "oTeDeum", defaultValue= "0")
    public Integer oTeDeum;

/*


    public void setOlSalmos(Integer olSalmos) {
        this.olSalmos = olSalmos;
    }
    public Integer getOlSalmos() {
        return olSalmos;
    }
    	`lHimnoFK` INTEGER NOT NULL DEFAULT 0,
	`lSalmodiaFK` INTEGER NOT NULL DEFAULT 0,
	`lBiblicaFK` INTEGER NOT NULL DEFAULT 0,
	`lOracionFK` INTEGER NOT NULL DEFAULT 0,
*/

    @NonNull
    @ColumnInfo(name = "lHimnoFK", defaultValue= "0")
    public Integer lHimnoFK;

    @NonNull
    @ColumnInfo(name = "lSalmodiaFK", defaultValue= "0")
    public Integer lSalmodiaFK;

    @NonNull
    @ColumnInfo(name = "lBiblicaFK", defaultValue= "0")
    public Integer lBiblicaFK;

    @NonNull
    @ColumnInfo(name = "lBenedictusFK", defaultValue= "0")
    public Integer lBenedictusFK;

    @NonNull
    @ColumnInfo(name = "lPrecesFK", defaultValue= "0")
    public Integer lPrecesFK;

    @NonNull
    @ColumnInfo(name = "lOracionFK", defaultValue= "0")
    public Integer lOracionFK;


    @NonNull
    @ColumnInfo(name = "tHimnoFK", defaultValue= "0")
    public Integer tHimnoFK;

    @NonNull
    @ColumnInfo(name = "tSalmodiaFK", defaultValue= "0")
    public Integer tSalmodiaFK;

    @NonNull
    @ColumnInfo(name = "tBiblicaFK", defaultValue= "0")
    public Integer tBiblicaFK;

    @NonNull
    @ColumnInfo(name = "tOracionFK", defaultValue= "0")
    public Integer tOracionFK;

    public void setVersion(Integer version) {
        this.version = version;
    }
    public Integer getVersion() {
        return version;
    }

    public Integer getHoy() {
        return hoy!=null ? hoy : 0;
    }
    public void setHoy(Integer hoy) {
        this.hoy = hoy;
    }

    public Integer getFeriaId() {
        return feriaId;
    }

    public void setFeriaId(Integer feriaId) {
        this.feriaId = feriaId;
    }

    public Integer getMLecturasFK() {
        return mLecturasFK;
    }

    public void setOtroId(Integer otroId) {
        this.mLecturasFK = otroId;
    }

    public Integer getPrevioId() {
        return previoId;
    }

    public void setPrevioId(Integer previoId) {
        this.previoId = previoId;
    }

    public Integer getTiempoId() {
        return tiempoId;
    }

    public void setTiempoId(Integer tiempoId) {
        this.tiempoId = tiempoId;
    }

    public Integer getOficioFK() {
        return oficioFK;
    }

    public void setOficioFK(Integer oficioFK) {
        this.oficioFK = oficioFK;
    }



}

