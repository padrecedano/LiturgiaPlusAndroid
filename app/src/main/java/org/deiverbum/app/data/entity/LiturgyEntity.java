package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "liturgy",
        indices = {@Index(value = {"timeFK","typeFK", "week", "day"}, unique = true)},

        foreignKeys =
        {
            @ForeignKey(
                    entity = LiturgyTimeEntity.class,
                    parentColumns = "timeID",
                    childColumns = "timeFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = LiturgyColorEntity.class,
                    parentColumns = "colorID",
                    childColumns = "colorFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        }
)
public class LiturgyEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "liturgyID")
    public Integer liturgiaId=0;

    @NonNull
    @ColumnInfo(name = "timeFK")
    public Integer tiempoFK=0;

    @NonNull
    @ColumnInfo(name = "typeFK")
    public Integer tipoFK=0;

    @NonNull
    @ColumnInfo(name = "week")
    public Integer semana=0;

    @NonNull
    @ColumnInfo(name = "day")
    public Integer dia=0;

    @NonNull
    @ColumnInfo(name = "colorFK", index = true)
    public Integer colorFK=0;

    @NonNull
    @ColumnInfo(name = "name")
    public String nombre="";

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


}

