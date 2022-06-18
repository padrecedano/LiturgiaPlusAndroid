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

@Entity(tableName = "lh_oficio_verso",
        indices = {@Index(value = {"verso"}, unique = true)})
public class LHOficioVersoEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "versoId")
    public Integer versoId=0;

    @NonNull
    @ColumnInfo(name = "verso")
    public String verso="";

    public String getResponsorio() {
        return verso;
    }
}

