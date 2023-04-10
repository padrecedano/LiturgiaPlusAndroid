package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_OFFICE_BIBLICAL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_OFFICE_BIBLICAL,
        primaryKeys = {"groupFK", "theOrder"},

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
    public Integer groupFK = 0;

    @NonNull
    @ColumnInfo(name = "readingFK", index = true)
    public Integer readingFK = 0;

    @NonNull
    @ColumnInfo(name = "responsoryFK", index = true)
    public Integer responsoryFK = 0;

    @NonNull
    @ColumnInfo(name = "theme")
    public String theme = "";

    @NonNull
    @ColumnInfo(name = "theOrder", defaultValue = "1")
    public Integer theOrder = 1;
}

