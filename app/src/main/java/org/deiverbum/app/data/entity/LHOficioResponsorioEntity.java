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

@Entity(tableName = "lh_oficio_responsorio",
        indices = {@Index(value = {"responsorio"}, unique = true)})
public class LHOficioResponsorioEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "responsorioId")
    public Integer responsorioId=0;


    @NonNull
    @ColumnInfo(name = "responsorio")
    public String responsorio="";

    public String getResponsorio() {
        return responsorio;
    }
}

