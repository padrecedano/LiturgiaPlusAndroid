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

@Entity(tableName = "lh_antiphon",
        indices = {@Index(value = {"antiphon"},unique = true)})
public class AntifonaEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "antiphonID")
    public Integer antifonaId=0;

    @NonNull
    @ColumnInfo(name = "antiphon")
    public String antifona="";


    public void setAntifona(@NonNull String antifona) {
        this.antifona = antifona;
    }
    public String getAntifona() {
        return antifona;
    }


    @NonNull
    public Integer getAntifonaId() {
        return antifonaId;
    }
    public void setAntifonaId(@NonNull Integer antifonaId) {
        this.antifonaId = antifonaId;
    }

}

