package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "liturgy_time",
        indices = {@Index(value = {"time"}, unique = true)}
)
public class LiturgiaTiempoEntity {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timeID")
    public Integer tiempoId=0;

    @NonNull
    @ColumnInfo(name = "time")
    public String tiempo="";

    @NonNull
    @ColumnInfo(name = "liturgyName")
    public String liturgyName="";

    @NonNull
    public Integer getTiempoId() {
        return tiempoId;
    }

    public void setTiempoId(@NonNull Integer tiempoId) {
        this.tiempoId = tiempoId;
    }

    @NonNull
    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(@NonNull String tiempo) {
        this.tiempo = tiempo;
    }

    @NonNull
    public String getLiturgyName() {
        return liturgyName;
    }

    public void setLiturgyName(@NonNull String liturgyName) {
        this.liturgyName = liturgyName;
    }
}

