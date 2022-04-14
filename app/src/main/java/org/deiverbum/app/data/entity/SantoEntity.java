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
    @ColumnInfo(name = "tipoId")
    public Integer tipoId;

    @NonNull
    @ColumnInfo(name = "mes")
    public Integer mes;

    @NonNull
    @ColumnInfo(name = "dia")
    public Integer dia;

    @NonNull
    @ColumnInfo(name = "nombre")
    public String nombre;

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

    @NonNull
    public String getMartirologio() {
        return martirologio;
    }

    @NonNull
    public String getLhVida() {
        return lhVida;
    }

    @NonNull
    @ColumnInfo(name = "martirologio")
    public String martirologio;

    @NonNull
    @ColumnInfo(name = "lh_vida")
    public String lhVida;

    @NonNull
    @ColumnInfo(name = "vida")
    public String vida;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public String getVida() {
        return vida;
    }

    public Santo getDomainModel(boolean isLongLife){
        Santo theModel=new Santo();
        theModel.setDia(String.valueOf(getDia()));
        theModel.setMes(String.valueOf(getMes()));
        theModel.setNombre(getNombre());
        if(isLongLife) {
            theModel.setVida(getVida());
        }else{
            theModel.setVida(getLhVida());
        }
        return theModel;
    }

}

