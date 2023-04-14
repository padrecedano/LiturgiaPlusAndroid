package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.BIBLE_HOMILY_JOIN;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = BIBLE_HOMILY_JOIN,
        primaryKeys = {"readingFK", "homilyFK"},
        foreignKeys =
                {

                        @ForeignKey(
                                entity = BibleReadingEntity.class,
                                parentColumns = "readingID",
                                childColumns = "readingFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(
                                entity = HomilyEntity.class,
                                parentColumns = "homilyID",
                                childColumns = "homilyFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)
                }
)
public class BibleHomilyJoinEntity {

    @NonNull
    @ColumnInfo(name = "readingFK")
    public Integer readingFK = 0;

    @NonNull
    @ColumnInfo(name = "homilyFK", index = true)
    public Integer homilyFK = 0;

    public int getReadingFK() {
        return readingFK;
    }

    public int getHomilyFK() {
        return homilyFK;
    }

}

