package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Liturgia;

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
    public Integer liturgiaId;

    @NonNull
    public Integer getLiturgiaId() {
        return liturgiaId;
    }

    @NonNull
    public Integer getTiempoFK() {
        return tiempoFK;
    }

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
    public Integer tiempoFK;

    @NonNull
    @ColumnInfo(name = "tipoFK")
    public Integer tipoFK;

    @NonNull
    @ColumnInfo(name = "semana")
    public Integer semana;

    @NonNull
    @ColumnInfo(name = "dia")
    public Integer dia;

    @NonNull
    @ColumnInfo(name = "colorFK")
    public Integer colorFK;

    @NonNull
    @ColumnInfo(name = "nombre")
    public String nombre;

    public Liturgia getDomainModel() {
        Liturgia theModel=new Liturgia();
        theModel.setLiturgiaId(getLiturgiaId());
        theModel.setColorId(getColorFK());
        theModel.setNombre(getNombre());
        return theModel;
    }
}

