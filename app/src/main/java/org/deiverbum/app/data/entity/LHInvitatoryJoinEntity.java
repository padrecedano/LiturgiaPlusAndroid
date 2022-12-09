package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_INVITATORY_JOIN;

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

@Entity(tableName = LH_INVITATORY_JOIN,
        foreignKeys =
        {
                @ForeignKey(
                        entity = LHInvitatoryEntity.class,
                        parentColumns = "caseID",
                        childColumns = "caseFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = LHAntiphonEntity.class,
                        parentColumns = "antiphonID",
                        childColumns = "antiphonFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LHInvitatoryJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "antiphonFK", index = true)
    public Integer antifonaFK=0;

    @NonNull
    @ColumnInfo(name = "caseFK", index = true)
    public Integer casoFK=0;

    public int getSalmoFK() {
        return  antifonaFK;
    }
    public int getGrupoId() {
        return grupoId;
    }

}

