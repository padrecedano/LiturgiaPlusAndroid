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

@Entity(tableName = "lh_invitatorio_join",
        //indices = {@Index(value = {"grupoFK","salmoFK"}, unique = true)}
        primaryKeys = {"grupoFK","salmoFK"},
        foreignKeys =
        {
                @ForeignKey(
                        entity = InvitatorioEntity.class,
                        parentColumns = "grupoId",
                        childColumns = "grupoFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = SalmoEntity.class,
                        parentColumns = "salmoId",
                        childColumns = "salmoFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LHInvitatorioJoinEntity {

    @NonNull
    //@PrimaryKey
    @ColumnInfo(name = "grupoFK")
    public Integer grupoFK;

    @NonNull
    @ColumnInfo(name = "salmoFK")
    public Integer salmoFK;

    public int getSalmoFK() {
        return this.salmoFK!=null ? salmoFK:0;
    }
    public int getGrupoFK() {
        return this.grupoFK!=null ? grupoFK:0;
    }

}

