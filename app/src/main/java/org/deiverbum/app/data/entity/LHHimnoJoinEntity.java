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

@Entity(tableName = "lh_himno_join",
        indices = {@Index(value = {"grupoId","himnoFK"}, unique = true)},
        //primaryKeys = {"grupoId","himnoFK"},
        foreignKeys =
        {

                @ForeignKey(
                        entity = HimnoEntity.class,
                        parentColumns = "himnoId",
                        childColumns = "himnoFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LHHimnoJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "grupoId")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "himnoFK", defaultValue = "0")
    public Integer himnoFK=0;

    public int getHimnoFK() {
        return himnoFK;
    }
    public int getGrupoId() {
        return grupoId;
    }

}

