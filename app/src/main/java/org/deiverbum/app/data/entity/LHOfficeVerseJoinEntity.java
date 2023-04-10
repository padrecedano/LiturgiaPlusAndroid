package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_OFFICE_VERSE_JOIN;

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

@Entity(tableName = LH_OFFICE_VERSE_JOIN,

        foreignKeys =
                {
                        @ForeignKey(
                                entity = LHOfficeVerseEntity.class,
                                parentColumns = "verseID",
                                childColumns = "verseFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)
                }
)
public class LHOfficeVerseJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId = 0;

    @NonNull
    @ColumnInfo(name = "verseFK", index = true)
    public Integer versoFK = 0;

    public int getResponsorioFK() {
        return versoFK;
    }

    public int getGrupoId() {
        return grupoId;
    }

}

