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

@Entity(tableName = "lh_preces",
        indices = {@Index(value = {"preces"}, unique = true)}
)
public class LHPrecesEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "precesId")
    public Integer precesId;


    @NonNull
    @ColumnInfo(name = "intro")
    public String intro;

    @NonNull
    @ColumnInfo(name = "preces")
    public String preces;

}

