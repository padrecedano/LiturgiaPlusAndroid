package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_office_biblical_join"
        //indices = {@Index(value = {"grupoFK","salmoFK"}, unique = true)}
        //primaryKeys = {"grupoFK","salmoFK"},
)
public class LHBiblicaOficioJoinEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;
}

