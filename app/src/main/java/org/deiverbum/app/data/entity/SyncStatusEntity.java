package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.SYNC_STATUS;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = SYNC_STATUS)

public class SyncStatusEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "tableName")
    public String tableName = "";

    @NonNull
    @ColumnInfo(name = "versionDB")
    public Integer versionDB = 1;

    @ColumnInfo(name = "lastUpdate", defaultValue = "CURRENT_TIMESTAMP")
    public String lastUpdate;

}

