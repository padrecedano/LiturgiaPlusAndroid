package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_invitatorio",
        foreignKeys =
        {
                @ForeignKey(
                        entity = AntifonaEntity.class,
                        parentColumns = "antifonaId",
                        childColumns = "antifonaFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class InvitatorioEntityForReview {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "invitatorioId")
    public Integer invitatorioId;

    @NonNull
    @ColumnInfo(name = "tipoId")
    public Integer tipoId;

    @NonNull
    @ColumnInfo(name = "antifonaFK")
    public Integer antifonaFK;


    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }
    public Integer getTipoId() {
        return tipoId;
    }


    public Integer getAntifonaFK() {
        return antifonaFK;
    }
    public void setAntifonaFK(Integer antifonaFK) {
        this.antifonaFK = antifonaFK;
    }

}

