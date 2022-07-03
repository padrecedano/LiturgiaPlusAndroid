package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.LiturgiaTiempo;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "liturgia",
        indices = {@Index(value = {"tiempoFK","tipoFK", "semana", "dia"}, unique = true)},

        foreignKeys =
        {
            @ForeignKey(
                    entity = LiturgiaTiempoEntity.class,
                    parentColumns = "tiempoId",
                    childColumns = "tiempoFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)/*,
            @ForeignKey(
                    entity = LHResponsorioEntity.class,
                    parentColumns = "responsorioId",
                    childColumns = "responsorioFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)*/
        }
)
public class LiturgiaEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "liturgiaId")
    public Integer liturgiaId=0;

    @NonNull
    public Integer getLiturgiaId() {
        return liturgiaId;
    }

    @SuppressWarnings("unused")
    @NonNull
    public Integer getTiempoFK() {
        return tiempoFK;
    }

    @SuppressWarnings("unused")
    @NonNull
    public Integer getTipoFK() {
        return tipoFK;
    }

    @NonNull
    public Integer getSemana() {
        return semana;
    }

    @NonNull
    public Integer getDia() {
        return dia;
    }

    @NonNull
    public Integer getColorFK() {
        return colorFK;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    @NonNull
    @ColumnInfo(name = "tiempoFK")
    public Integer tiempoFK=0;

    @NonNull
    @ColumnInfo(name = "tipoFK")
    public Integer tipoFK=0;

    @NonNull
    @ColumnInfo(name = "semana")
    public Integer semana=0;

    @NonNull
    @ColumnInfo(name = "dia")
    public Integer dia=0;

    @NonNull
    @ColumnInfo(name = "colorFK")
    public Integer colorFK=0;

    @NonNull
    @ColumnInfo(name = "nombre")
    public String nombre="";

    //@Embedded
    //public LiturgiaTiempo liturgiaTiempo;


}

