package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "obra",
        foreignKeys =
                {
                        @ForeignKey(
                                entity = PadreEntity.class,
                                parentColumns = "padreId",
                                childColumns = "padreFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)},
         indices = {@Index(value = {"padreFK","obra"},unique = true)}
)
public class ObraEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "obraId")
    public Integer obraId;

    @NonNull
    @ColumnInfo(name = "padreFK")
    public Integer padreFK;

    @NonNull
    @ColumnInfo(name = "obra")
    public String obra;

    public String getObra() {
        return obra!=null ? obra : "";
    }
}

