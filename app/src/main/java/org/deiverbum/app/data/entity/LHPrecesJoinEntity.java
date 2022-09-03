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

@Entity(tableName = "lh_intercessions_join",
    foreignKeys =
    {
        @ForeignKey(
                    entity = LHPrecesEntity.class,
                    parentColumns = "intercessionID",
                    childColumns = "intercessionFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
                    )
    }
)
public class LHPrecesJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "intercessionFK")
    public Integer precesFK=0;
}

