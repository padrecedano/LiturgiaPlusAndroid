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

@Entity(tableName = "lh_invitatorio_join",
        //indices = {@Index(value = {"grupoFK","salmoFK"}, unique = true)}
        //primaryKeys = {"grupoFK","salmoFK"},
        foreignKeys =
        {
                @ForeignKey(
                        entity = InvitatorioEntity.class,
                        parentColumns = "caseID",
                        childColumns = "caseFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = AntifonaEntity.class,
                        parentColumns = "antiphonID",
                        childColumns = "antiphonFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LHInvitatorioJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "antiphonFK")
    public Integer antifonaFK=0;

    @NonNull
    @ColumnInfo(name = "caseFK")
    public Integer casoFK=0;

    public int getSalmoFK() {
        return  antifonaFK;
    }
    public int getGrupoId() {
        return grupoId;
    }

}

