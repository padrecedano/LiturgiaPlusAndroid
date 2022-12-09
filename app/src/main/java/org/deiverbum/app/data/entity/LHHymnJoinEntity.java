package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_HYMN_JOIN;

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

@Entity(tableName = LH_HYMN_JOIN,
        indices = {@Index(value = {"groupID","hymnFK"}, unique = true)},
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
    @ColumnInfo(name = "hymnFK",index = true)
    public Integer himnoFK=0;

    public int getHimnoFK() {
        return himnoFK;
    }
    public int getGrupoId() {
        return grupoId;
    }

}

