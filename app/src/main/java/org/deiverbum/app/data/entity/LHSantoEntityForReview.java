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

@Entity(
        tableName = "lh_santo",
        indices = {@Index(value = {"nombre"},unique = true)}
)
public class LHSantoEntityForReview {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "santoId")
    public Integer santoId;

    @NonNull
    @ColumnInfo(name = "nombre")
    public String nombre;

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

}

