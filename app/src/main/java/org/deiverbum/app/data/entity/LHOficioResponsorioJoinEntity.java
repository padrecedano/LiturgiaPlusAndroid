package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_oficio_responsorio_join",
        //indices = {@Index(value = {"grupoId","himnoFK"}, unique = true)},
        //primaryKeys = {"grupoId","himnoFK"},
        foreignKeys =
        {

                @ForeignKey(
                        entity = LHOficioResponsorioEntity.class,
                        parentColumns = "responsorioId",
                        childColumns = "responsorioFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LHOficioResponsorioJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "grupoId")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "responsorioFK")
    public Integer responsorioFK=0;

    public int getResponsorioFK() {
        return responsorioFK;
    }
    public int getGrupoId() {
        return grupoId;
    }

}

