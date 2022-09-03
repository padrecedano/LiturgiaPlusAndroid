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

@Entity(tableName = "lh_hymn", indices = {@Index(value = {"hymn"},unique =
        true)})
public class HimnoEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hymnID")
    public Integer himnoId=0;

    @NonNull
    @ColumnInfo(name = "hymn")
    public String himno="";

    public String getHimno() {
        return himno;
    }
}

