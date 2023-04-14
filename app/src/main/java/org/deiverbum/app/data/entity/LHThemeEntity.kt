package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_THEME;

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

@Entity(tableName = LH_THEME,
        indices = {@Index(value = {"theme"}, unique = true)}
)
public class LHThemeEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "themeID")
    public Integer temaId = 0;

    @NonNull
    @ColumnInfo(name = "theme")
    public String tema = "";

    @NonNull
    public String getTema() {
        return tema;
    }
}

