package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Saint;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 * CREATE TABLE `santo` (
 * 	`santoId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 * 	`liturgiaId` INTEGER NOT NULL,
 * 	`nombre` TEXT NOT NULL,
 * 	`mes` INTEGER NOT NULL,
 * 	`dia` INTEGER NOT NULL,
 * 	`tipoId` INTEGER NOT NULL,
 * 	`momentoId` INTEGER DEFAULT 0, -- '1:Vigilia 2:Dia',
 * 	`status` INTEGER DEFAULT 0,   -- '0:HomilyList No, 1:HomilyList Yes',
 * 	`comunId` INTEGER DEFAULT 0,
 * 	UNIQUE(`nombre`,`mes`,`dia`)
 *  );
 */

@Entity(
        tableName = "saint",
        indices = {@Index(value = {"liturgyFK","theName","theMonth","theDay"},unique = true)},
        foreignKeys =
                {

                        @ForeignKey(
                                entity = LiturgyEntity.class,
                                parentColumns = "liturgyID",
                                childColumns = "liturgyFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)
                }
)
public class SaintEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saintID")
    public Integer santoId=0;

    @NonNull
    @ColumnInfo(name = "liturgyFK")
    public Integer liturgiaFK=0;

    @NonNull
    @ColumnInfo(name = "theName")
    public String nombre="";

    @NonNull
    @ColumnInfo(name = "theMonth")
    public Integer mes=0;

    @NonNull
    @ColumnInfo(name = "theDay")
    public Integer dia=0;

    @NonNull
    @ColumnInfo(name = "typeFK")
    public Integer tipoId=0;

    @ColumnInfo(name = "commonFK", defaultValue="0")
    public Integer comunId=0;

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

    public void setTheName(String nombre) {
        this.nombre = nombre;
    }
    public String getTheName() {
        return nombre;
    }

    public Saint getDomainModel(boolean isLongLife){
        Saint theModel=new Saint();
        theModel.setDay(String.valueOf(getTheDay()));
        theModel.setMonth(String.valueOf(getTheMonth()));
        theModel.setTheName(getTheName());
        return theModel;
    }

}

