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
    public Integer grupoId;

    @NonNull
    @ColumnInfo(name = "responsorioFK")
    public Integer responsorioFK;

    public int getResponsorioFK() {
        return this.responsorioFK!=null ? responsorioFK:0;
    }
    public int getGrupoId() {
        return this.grupoId!=null ? grupoId:0;
    }

}

