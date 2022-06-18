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
    public Integer casoId=0;

    @NonNull
    @ColumnInfo(name = "salmoFK")
    public Integer salmoFK=0;

    @NonNull
    public Integer getSalmoFK() {
        return salmoFK;
    }
    public void setSalmoFK(@NonNull Integer salmoFK) {
        this.salmoFK = salmoFK;
    }

    @SuppressWarnings("unused")
    @NonNull
    public Integer getCasoId() {
        return casoId;
    }
    @SuppressWarnings("unused")
    public void setCasoId(@NonNull Integer casoId) {
        this.casoId = casoId;
    }

}

