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

@Entity(tableName = "lh_theme",
         indices = {@Index(value = {"theme"},unique = true)}
)
public class TemaEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "themeID")
    public Integer temaId=0;

    @NonNull
    @ColumnInfo(name = "theme")
    public String tema="";

    public String getTema(){
        return tema;
    }
}

