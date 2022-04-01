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
                        entity = OficioEntity.class,
                        parentColumns = "oficioId",
                        childColumns = "oficioFK",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE)})
public class Today {
    @PrimaryKey
    @ColumnInfo(name = "hoy")
    public Integer hoy;

    @NonNull
    @ColumnInfo(name = "feriaId")
    public Integer feriaId;

    @ColumnInfo(name = "otroId")
    public Integer otroId;

    @ColumnInfo(name = "previoId")
    public Integer previoId;

    @NonNull
    @ColumnInfo(name = "tiempoId")
    public Integer tiempoId;

    @ColumnInfo(name = "version")
    public Integer version;

    @NonNull
    @ColumnInfo(name = "olSalmos")
    public Integer olSalmos;

    //@NonNull
    @ColumnInfo(name = "oficioFK")
    public Integer oficioFK;


    public void setOlSalmos(Integer olSalmos) {
        this.olSalmos = olSalmos;
    }
    public Integer getOlSalmos() {
        return olSalmos;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    public Integer getVersion() {
        return version;
    }

    public Integer getHoy() {
        return hoy;
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

    public Integer getOtroId() {
        return otroId;
    }

    public void setOtroId(Integer otroId) {
        this.otroId = otroId;
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

