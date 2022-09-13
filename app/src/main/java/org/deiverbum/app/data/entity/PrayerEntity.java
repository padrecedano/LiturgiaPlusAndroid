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

@Entity(tableName = "prayer"/*,
         indices = {@Index(value = {"prayer"},unique = true)}*/
)
public class PrayerEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "prayerID")
    public Integer oracionId=0;

    @NonNull
    @ColumnInfo(name = "prayer")
    public String texto="";

    @NonNull
    @ColumnInfo(name = "order")
    public Integer orden=0;

    public String getTexto() {
        return texto;
    }
}

