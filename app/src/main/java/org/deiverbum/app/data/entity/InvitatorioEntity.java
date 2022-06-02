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

@Entity(tableName = "lh_invitatorio",

        foreignKeys =
        {
                @ForeignKey(
                        entity = SalmoEntity.class,
                        parentColumns = "salmoId",
                        childColumns = "salmoFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class InvitatorioEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "casoId")
    public Integer casoId;

    /*
    @NonNull
    @ColumnInfo(name = "tipoId")
    public Integer tipoId;
*/
    @NonNull
    @ColumnInfo(name = "salmoFK")
    public Integer salmoFK;

/*
    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }
    public Integer getTipoId() {
        return tipoId;
    }
*/

    public Integer getSalmoFK() {
        return salmoFK;
    }
    public void setSalmoFK(Integer salmoFK) {
        this.salmoFK = salmoFK;
    }

    public Integer getCasoId() {
        return casoId;
    }
    public void setCasoId(Integer casoId) {
        this.casoId = casoId;
    }

}

