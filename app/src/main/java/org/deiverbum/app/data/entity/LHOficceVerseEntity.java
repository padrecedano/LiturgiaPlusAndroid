package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_office_verse",
        indices = {@Index(value = {"verse"}, unique = true)})
public class LHOficceVerseEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "verseID")
    public Integer versoId=0;

    @NonNull
    @ColumnInfo(name = "verse")
    public String verso="";

    public String getResponsorio() {
        return verso;
    }
}
