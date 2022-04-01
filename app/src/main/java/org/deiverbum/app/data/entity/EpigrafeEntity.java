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

@Entity(tableName = "lh_epigrafe",
        indices = {@Index(value = {"epigrafe"}, unique = true)})
public class EpigrafeEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "epigrafeId")
    public Integer epigrafeId;

    @NonNull
    @ColumnInfo(name = "epigrafe")
    public String epigrafe;

    public String getEpigrafe() {
        String a="A";
        return (epigrafe!=null) ? epigrafe : "";
    }
}

