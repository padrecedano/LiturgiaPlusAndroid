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

@Entity(tableName = "db_table"/*,
        indices = {@Index(value = {"antiphon"},unique = true)}*/)
public class DBTableEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tableID")
    public Integer tableID=0;

    @NonNull
    @ColumnInfo(name = "tableName")
    public String tableName="";

}

