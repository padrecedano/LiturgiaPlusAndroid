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
 * @since 2023.1
 */

@Entity(tableName = "liturgy_group", indices = {@Index(value = {"liturgyFK"}, unique =
        true)},
        foreignKeys =
                {
                        @ForeignKey(
                                entity = LiturgyEntity.class,
                                parentColumns = "liturgyID",
                                childColumns = "liturgyFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)})
public class LiturgyGroupEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer groupID = 0;

    @NonNull
    @ColumnInfo(name = "liturgyFK")
    public Integer liturgyFK = 0;

}

