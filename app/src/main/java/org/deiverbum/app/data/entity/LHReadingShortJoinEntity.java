package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_READING_SHORT_JOIN;

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

@Entity(tableName = LH_READING_SHORT_JOIN,
        foreignKeys =
                {
                   @ForeignKey(
                           entity = LHReadingShortEntity.class,
                           parentColumns = "readingID",
                           childColumns = "readingFK",
                           onDelete = ForeignKey.CASCADE,
                           onUpdate = ForeignKey.CASCADE),
                   @ForeignKey(
                           entity = LHResponsoryShortEntity.class,
                           parentColumns = "responsoryID",
                           childColumns = "responsoryFK",
                           onDelete = ForeignKey.CASCADE,
                           onUpdate = ForeignKey.CASCADE)
                }
)
public class LHReadingShortJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "readingFK",index = true)
    public Integer lecturaFK=0;

    @NonNull
    @ColumnInfo(name = "responsoryFK",index = true)
    public Integer responsorioFK=0;

    public int getGrupoId() {
        return  grupoId;
    }

}

