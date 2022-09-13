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

@Entity(tableName = "lh_gospel_canticle",

        foreignKeys =
        {
            @ForeignKey(
                    entity = AntiphonEntity.class,
                    parentColumns = "antiphonID",
                    childColumns = "antiphonFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)/*,
            @ForeignKey(
                    entity = LHResponsoryEntity.class,
                    parentColumns = "responsorioId",
                    childColumns = "responsorioFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)*/
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

