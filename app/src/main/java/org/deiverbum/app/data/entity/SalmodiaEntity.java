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

@Entity(tableName = "lh_salmodia",
        primaryKeys = {"liturgiaId", "pericopaFK", "antifonaFK"},
        foreignKeys =
        {
            @ForeignKey(
                    entity = BibliaLecturaEntity.class,
                    parentColumns = "pericopaId",
                    childColumns = "pericopaFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = AntifonaEntity.class,
                    parentColumns = "antifonaId",
                    childColumns = "antifonaFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                        entity = TemaEntity.class,
                        parentColumns = "temaId",
                        childColumns = "temaFK",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                        entity = EpigrafeEntity.class,
                        parentColumns = "epigrafeId",
                        childColumns = "epigrafeFK",
                        onDelete = ForeignKey.SET_DEFAULT,
                        onUpdate = ForeignKey.CASCADE)
        }/*,
        indices={@Index(value={"liturgiaId","salmoFK", "antifonaFK"},unique =
         true)}*/
)
public class SalmodiaEntity {
/*    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "salmodiaId")
    public Integer salmodiaId;
*/
    @NonNull
    @ColumnInfo(name = "liturgiaId")
    public Integer liturgiaId;

    @NonNull
    @ColumnInfo(name = "pericopaFK")
    public Integer salmoFK;

    @NonNull
    @ColumnInfo(name = "orden")
    public Integer orden;

    @NonNull
    @ColumnInfo(name = "antifonaFK")
    public Integer antifonaId;

    @ColumnInfo(name = "temaFK")
    public Integer temaFK;

    @ColumnInfo(name = "epigrafeFK")
    public Integer epigrafeFK;

    @ColumnInfo(name = "parte")
    public Integer parte;
/*
    @Embedded
    public List<Salmo> salmos;

    public Salmodia() {
    }

        public List<Salmo> getSalmos() {
        return salmos;
    }
    public void setSalmo(List<Salmo> salmos) {
        this.salmos = salmos;
    }
*/
/*
    public void setSalmodiaId(Integer salmodiaId) {
        this.salmodiaId = salmodiaId;
    }
    public Integer getSalmodiaId() {
        return salmodiaId;
    }
*/
    public void setLiturgiaId(Integer liturgiaId) {
        this.liturgiaId = liturgiaId;
    }
    public Integer getLiturgiaId() {
        return liturgiaId;
    }

    public Integer getSalmoFK() {
        return salmoFK;
    }
    public void setSalmoFK(Integer salmoFK) {
        this.salmoFK = salmoFK;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public void setAntifonaId(Integer antifonaId) {
        this.antifonaId = antifonaId;
    }
    public Integer getAntifonaId() {
        return antifonaId;
    }

    public String getParte() {
        return parte!=null ? String.valueOf(parte) : "";
    }
}

