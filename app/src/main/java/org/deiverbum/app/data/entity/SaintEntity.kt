package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.SAINT;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Saint;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 * CREATE TABLE `santo` (
 * `santoId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 * `liturgiaId` INTEGER NOT NULL,
 * `nombre` TEXT NOT NULL,
 * `mes` INTEGER NOT NULL,
 * `dia` INTEGER NOT NULL,
 * `tipoId` INTEGER NOT NULL,
 * `momentoId` INTEGER DEFAULT 0, -- '1:Vigilia 2:Dia',
 * `status` INTEGER DEFAULT 0,   -- '0:HomilyList No, 1:HomilyList Yes',
 * `comunId` INTEGER DEFAULT 0,
 * UNIQUE(`nombre`,`mes`,`dia`)
 * );
 */

@Entity(
        tableName = SAINT,
        indices = {@Index(value = {"theName", "theMonth", "theDay"}, unique = true)}
)

public class SaintEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saintID")
    public Integer santoId = 0;

    @NonNull
    @ColumnInfo(name = "theName")
    public String nombre = "";

    @NonNull
    @ColumnInfo(name = "theMonth")
    public Integer mes = 0;

    @NonNull
    @ColumnInfo(name = "theDay")
    public Integer dia = 0;

    @NonNull
    @ColumnInfo(name = "typeFK", defaultValue = "0")
    public Integer tipoId = 0;

    @NonNull
    @ColumnInfo(name = "commonFK", defaultValue = "0")
    public Integer comunId = 0;

    @SuppressWarnings("unused")
    @NonNull
    public Integer getTipoId() {
        return tipoId;
    }

    @NonNull
    public Integer getTheMonth() {
        return mes;
    }

    @NonNull
    public Integer getTheDay() {
        return dia;
    }

    public String getTheName() {
        return nombre;
    }

    public void setTheName(String nombre) {
        this.nombre = nombre;
    }

    public Saint getDomainModel() {
        Saint theModel = new Saint();
        theModel.setDay(String.valueOf(getTheDay()));
        theModel.setMonth(String.valueOf(getTheMonth()));
        theModel.setTheName(getTheName());
        return theModel;
    }

}

