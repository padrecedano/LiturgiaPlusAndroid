package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.PRAYER;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Pater;
import org.deiverbum.app.model.Prayer;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = PRAYER)
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

    @NonNull
    public String getTexto() {
        return texto;
    }

    public Prayer getDomainModel() {
        Prayer dm=new Prayer();
        dm.setPrayer(texto);
        return dm;
    }

}

