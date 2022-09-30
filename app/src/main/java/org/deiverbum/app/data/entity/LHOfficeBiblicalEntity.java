package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_OFFICE_BIBLICAL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = LH_OFFICE_BIBLICAL,
        primaryKeys = {"groupFK","readingFK","responsoryFK"},

        foreignKeys =
        {
           @ForeignKey(
                   entity = LHOfficeBiblicalJoinEntity.class,
                   parentColumns = "groupID",
                   childColumns = "groupFK",
                   onDelete = ForeignKey.CASCADE,
                   onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = BibleReadingEntity.class,
                    parentColumns = "readingID",
                    childColumns = "readingFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = LHResponsoryEntity.class,
                    parentColumns = "responsoryID",
                    childColumns = "responsoryFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        }
)

public class LHOfficeBiblicalEntity {
    @NonNull
    @ColumnInfo(name = "groupFK")
    public Integer grupoFK=0;

    @NonNull
    @ColumnInfo(name = "readingFK")
    public Integer lecturaFK=0;

    @NonNull
    @ColumnInfo(name = "responsoryFK")
    public Integer responsorioFK=0;

    @NonNull
    @ColumnInfo(name = "theme")
    public String tema="";

    @NonNull
    @ColumnInfo(name = "order", defaultValue= "1")
    public Integer orden=0;
}

