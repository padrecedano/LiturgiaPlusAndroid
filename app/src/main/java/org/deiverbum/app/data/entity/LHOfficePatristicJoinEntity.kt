package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_OFFICE_PATRISTIC_JOIN;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_OFFICE_PATRISTIC_JOIN)

public class LHOfficePatristicJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId = 0;
}

