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

@Entity(tableName = "liturgia_tiempo",
        indices = {@Index(value = {"tiempo"}, unique = true)}
)
public class LiturgiaTiempoEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "tiempoId")
    public Integer tiempoId;


    @NonNull
    @ColumnInfo(name = "tiempo")
    public String tiempo;

}

