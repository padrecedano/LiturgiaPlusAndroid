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

@Entity(tableName = "lh_intercessions",
        indices = {@Index(value = {"intro","intercession"}, unique = true)}
)
public class LHIntercessionsEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "intercessionID")
    public Integer precesId=0;


    @NonNull
    @ColumnInfo(name = "intro")
    public String intro="";

    @NonNull
    @ColumnInfo(name = "intercession")
    public String preces="";

}

