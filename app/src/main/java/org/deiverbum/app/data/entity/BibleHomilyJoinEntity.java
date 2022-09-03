package org.deiverbum.app.data.entity;

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

@Entity(tableName = "bible_homily_join",
        //indices = {@Index(value = {"readingFK","homilyFK"}, unique = true)},
        primaryKeys = {"readingFK","homilyFK"},
        foreignKeys =
        {

                @ForeignKey(
                        entity = BibliaLecturaEntity.class,
                        parentColumns = "readingID",
                        childColumns = "readingFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = HomiliaEntity.class,
                        parentColumns = "homilyID",
                        childColumns = "homilyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)

        }

)
public class BibleHomilyJoinEntity {

    @NonNull
    //@PrimaryKey
    @ColumnInfo(name = "readingFK")
    public Integer readingFK=0;

    @NonNull
    @ColumnInfo(name = "homilyFK")
    public Integer homilyFK=0;

    public int getReadingFK() {
        return readingFK;
    }
    public int getHomilyFK() {
        return homilyFK;
    }

}

