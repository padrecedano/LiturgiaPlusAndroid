package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_OFFICE_VERSE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_OFFICE_VERSE,
        indices = {@Index(value = {"verse"}, unique = true)})
public class LHOfficeVerseEntity {

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

