package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_INTERCESSIONS_JOIN;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_INTERCESSIONS_JOIN,
    foreignKeys =
    {
        @ForeignKey(
                    entity = LHIntercessionsEntity.class,
                    parentColumns = "intercessionID",
                    childColumns = "intercessionFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
                    )
    }
)

public class LHIntercessionsJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "intercessionFK",index = true)
    public Integer precesFK=0;
}

