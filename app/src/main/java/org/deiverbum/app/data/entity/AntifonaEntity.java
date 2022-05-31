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

@Entity(tableName = "lh_antifona",
        indices = {@Index(value = {"antifona"},unique = true)})
public class AntifonaEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "antifonaId")
    public Integer antifonaId;

    @NonNull
    @ColumnInfo(name = "antifona")
    public String antifona;


    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }
    public String getAntifona() {
        return antifona!=null ? antifona:"";
    }


    public Integer getAntifonaId() {
        return antifonaId;
    }
    public void setAntifonaId(Integer antifonaId) {
        this.antifonaId = antifonaId;
    }

}

