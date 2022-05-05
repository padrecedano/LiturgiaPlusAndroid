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

@Entity(tableName = "lh_salmodia_join",
        indices = {@Index(value = {"grupoId","tipo"}, unique = true)}
)
public class LHSalmodiaJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "grupoId")
    public Integer grupoId;

    @NonNull
    @ColumnInfo(name = "tipo")
    public Integer tipo;

    public int getTipo() {
        return this.tipo!=null ? tipo:0;
    }
}
