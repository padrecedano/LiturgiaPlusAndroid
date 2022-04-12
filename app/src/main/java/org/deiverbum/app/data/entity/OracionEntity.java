package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "oracion",
         indices = {@Index(value = {"oracion"},unique = true)}
)
public class OracionEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "oracionId")
    public Integer oracionId;

    @NonNull
    @ColumnInfo(name = "oracion")
    public String texto;

}

