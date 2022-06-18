package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_salmodia",
        primaryKeys = {"grupoFK", "pericopaFK", "antifonaFK"},
        foreignKeys =
        {
        @ForeignKey(
                   entity = LHSalmodiaJoinEntity.class,
                   parentColumns = "grupoId",
                   childColumns = "grupoFK",
                   onDelete = ForeignKey.CASCADE,
                   onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = SalmoEntity.class,
                    parentColumns = "salmoId",
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

    @NonNull
    @ColumnInfo(name = "grupoFK")
    public Integer grupoFK=0;

    @NonNull
    @ColumnInfo(name = "pericopaFK")
    public Integer salmoFK=0;

    @NonNull
    @ColumnInfo(name = "orden")
    public Integer orden=0;

    @NonNull
    @ColumnInfo(name = "antifonaFK", defaultValue = "0")
    public Integer antifonaId=0;

    @ColumnInfo(name = "temaFK")
    public Integer temaFK=0;

    @ColumnInfo(name = "epigrafeFK")
    public Integer epigrafeFK=0;

    @ColumnInfo(name = "parte")
    public Integer parte=0;

    public void setGrupoFK(@NonNull Integer grupoFK) {
        this.grupoFK = grupoFK;
    }
    @NonNull
    public Integer getGrupoFK() {
        return grupoFK;
    }

    @NonNull
    public Integer getSalmoFK() {
        return salmoFK;
    }
    public void setSalmoFK(@NonNull Integer salmoFK) {
        this.salmoFK = salmoFK;
    }

    @NonNull
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(@NonNull Integer orden) {
        this.orden = orden;
    }

    @SuppressWarnings("unused")
    public void setAntifonaId(@NonNull Integer antifonaId) {
        this.antifonaId = antifonaId;
    }

    @SuppressWarnings("unused")
    @NonNull
    public Integer getAntifonaId() {
        return antifonaId;
    }

    public String getParte() {
        return  String.valueOf(parte);
    }


}

