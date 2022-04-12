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

@Entity(tableName = "padre",
        indices={@Index(value={"padre"},unique = true)}
)
public class PadreEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "padreId")
    public Integer padreId;

    @NonNull
    @ColumnInfo(name = "padre")
    public String padre;


    public String getPadre() {
        return padre!=null ? padre : "";
    }
}

