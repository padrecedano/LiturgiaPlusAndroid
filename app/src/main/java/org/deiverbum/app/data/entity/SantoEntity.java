package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Santo;

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
 * 	`status` INTEGER DEFAULT 0,   -- '0:Homilia No, 1:Homilia Yes',
 * 	`comunId` INTEGER DEFAULT 0,
 * 	UNIQUE(`nombre`,`mes`,`dia`)
 *  );
 */

@Entity(
        tableName = "santo",
        indices = {@Index(value = {"nombre","mes","dia"},unique = true)}
)
public class SantoEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "santoId")
    public Integer santoId;

    @NonNull
    @ColumnInfo(name = "liturgiaId")
    public Integer liturgiaId;

    @NonNull
    @ColumnInfo(name = "nombre")
    public String nombre;

    @NonNull
    @ColumnInfo(name = "mes")
    public Integer mes;

    @NonNull
    @ColumnInfo(name = "dia")
    public Integer dia;

    @NonNull
    @ColumnInfo(name = "tipoId")
    public Integer tipoId;

    @ColumnInfo(name = "momentoId", defaultValue="0")
    public Integer momentoId;

    @ColumnInfo(name = "status", defaultValue="0")
    public Integer status;

    @ColumnInfo(name = "comunId", defaultValue="0")
    public Integer comunId;

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



    public Santo getDomainModel(boolean isLongLife){
        Santo theModel=new Santo();
        theModel.setDia(String.valueOf(getDia()));
        theModel.setMes(String.valueOf(getMes()));
        theModel.setNombre(getNombre());
        if(isLongLife) {
            //theModel.setVida(getVida());
        }else{
            //theModel.setVida(getLhVida());
        }
        return theModel;
    }

}

