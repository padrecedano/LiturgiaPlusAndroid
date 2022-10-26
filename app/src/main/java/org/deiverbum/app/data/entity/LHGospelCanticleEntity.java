package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_GOSPEL_CANTICLE;

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

@Entity(tableName = LH_GOSPEL_CANTICLE,

        foreignKeys =
        {
            @ForeignKey(
                    entity = LHAntiphonEntity.class,
                    parentColumns = "antiphonID",
                    childColumns = "antiphonFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        }
)
public class LHGospelCanticleEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "antiphonFK")
    public Integer antifonaFK=0;

}

