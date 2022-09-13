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

@Entity(tableName = "lh_office_verse_join",
        //indices = {@Index(value = {"grupoId","himnoFK"}, unique = true)},
        //primaryKeys = {"grupoId","himnoFK"},
        foreignKeys =
        {

                @ForeignKey(
                        entity = LHOficceVerseEntity.class,
                        parentColumns = "verseID",
                        childColumns = "verseFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LHOficceVerseJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "verseFK")
    public Integer versoFK=0;

    public int getResponsorioFK() {
        return versoFK;
    }

    public int getGrupoId() {
        return grupoId;
    }

}

