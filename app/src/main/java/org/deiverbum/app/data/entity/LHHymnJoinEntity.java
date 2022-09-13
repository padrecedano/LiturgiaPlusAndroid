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

@Entity(tableName = "lh_hymn_join",
        indices = {@Index(value = {"groupID","hymnFK"}, unique = true)},
        //primaryKeys = {"grupoId","himnoFK"},
        foreignKeys =
        {

                @ForeignKey(
                        entity = LHHymnEntity.class,
                        parentColumns = "hymnID",
                        childColumns = "hymnFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LHHymnJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "hymnFK")
    public Integer himnoFK=0;

    public int getHimnoFK() {
        return himnoFK;
    }
    public int getGrupoId() {
        return grupoId;
    }

}

