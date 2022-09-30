package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_HYMN;

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

@Entity(tableName = LH_HYMN,
        indices = {@Index(value = {"hymn"}, unique = true)}
)

public class LHHymnEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hymnID")
    public Integer himnoId = 0;

    @NonNull
    @ColumnInfo(name = "hymn")
    public String himno = "";

    @NonNull
    public String getHimno() {
        return himno;
    }
}

