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

@Entity(tableName = "lh_preces_join",
    foreignKeys =
    {
        @ForeignKey(
                    entity = LHPrecesEntity.class,
                    parentColumns = "precesId",
                    childColumns = "precesFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
                    )
    }
)
public class LHPrecesJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "grupoId")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "precesFK")
    public Integer precesFK=0;
}

