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
        indices = {@Index(value = {"liturgyFK","name","month","day"},unique = true)},
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
    @ColumnInfo(name = "name")
    public String nombre="";

    @NonNull
    @ColumnInfo(name = "month")
    public Integer mes=0;

    @NonNull
    @ColumnInfo(name = "day")
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
    public Integer getMes() {
        return mes;
    }

    @NonNull
    public Integer getDia() {
        return dia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public Saint getDomainModel(boolean isLongLife){
        Saint theModel=new Saint();
        theModel.setDia(String.valueOf(getDia()));
        theModel.setMes(String.valueOf(getMes()));
        theModel.setNombre(getNombre());
        return theModel;
    }

}

