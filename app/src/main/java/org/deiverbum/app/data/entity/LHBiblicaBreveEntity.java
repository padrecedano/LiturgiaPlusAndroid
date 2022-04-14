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

@Entity(tableName = "lh_biblica_breve",
        //indices = {@Index(value = {"pericopaFK"}, unique = true)},

        foreignKeys =
        {
            @ForeignKey(
                    entity = BibliaLecturaEntity.class,
                    parentColumns = "pericopaId",
                    childColumns = "pericopaFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = LHResponsorioEntity.class,
                    parentColumns = "responsorioId",
                    childColumns = "responsorioFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        }
)
public class LHBiblicaBreveEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "grupoId")
    public Integer grupoId;

    @NonNull
    @ColumnInfo(name = "pericopaFK")
    public Integer pericopaFK;

    @NonNull
    @ColumnInfo(name = "responsorioFK")
    public Integer responsorioFK;

}

