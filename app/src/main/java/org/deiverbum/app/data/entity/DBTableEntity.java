package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.DB_TABLE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = DB_TABLE)
public class DBTableEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tableID")
    public Integer tableID = 0;

    @NonNull
    @ColumnInfo(name = "tableName")
    public String tableName = "";

}

