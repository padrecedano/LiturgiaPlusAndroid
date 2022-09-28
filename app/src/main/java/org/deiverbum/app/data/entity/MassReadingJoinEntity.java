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

@Entity(tableName = "mass_reading_join",
        indices = {@Index(value = {"liturgyFK","type"}, unique = true)},
        foreignKeys =
                {
                        @ForeignKey(
                                entity = LiturgyEntity.class,
                                parentColumns = "liturgyID",
                                childColumns = "liturgyFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)
                }
)
public class MassReadingJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "liturgyFK")
    public Integer liturgyFK=0;

    @NonNull
    @ColumnInfo(name = "type")
    public Integer type=0;

    public int getType() {
        return type;
    }
}

