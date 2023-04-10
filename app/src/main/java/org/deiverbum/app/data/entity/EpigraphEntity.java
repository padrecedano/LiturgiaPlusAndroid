package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_EPIGRAPH;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_EPIGRAPH,
        indices = {@Index(value = {"epigraph"}, unique = true)})
public class EpigraphEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "epigraphID")
    public Integer epigrafeId = 0;

    @NonNull
    @ColumnInfo(name = "epigraph")
    public String epigrafe = "";

    @NonNull
    public String getEpigrafe() {
        return epigrafe;
    }
}

