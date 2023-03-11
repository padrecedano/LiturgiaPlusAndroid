package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LITURGY_TIME;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LITURGY_TIME
)

public class LiturgyTimeEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timeID")
    public Integer tiempoId=0;

    @NonNull
    @ColumnInfo(name = "timeName")
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

