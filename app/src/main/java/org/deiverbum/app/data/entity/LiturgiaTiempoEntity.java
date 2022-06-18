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

@Entity(tableName = "liturgia_tiempo",
        indices = {@Index(value = {"tiempo"}, unique = true)}
)
public class LiturgiaTiempoEntity {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tiempoId")
    public Integer tiempoId=0;


    @NonNull
    @ColumnInfo(name = "tiempo")
    public String tiempo="";

    @NonNull
    @ColumnInfo(name = "liturgyName")
    public String liturgyName="";

}

