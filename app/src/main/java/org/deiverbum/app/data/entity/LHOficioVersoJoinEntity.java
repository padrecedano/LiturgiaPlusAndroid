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

@Entity(tableName = "lh_oficio_verso_join",
        //indices = {@Index(value = {"grupoId","himnoFK"}, unique = true)},
        //primaryKeys = {"grupoId","himnoFK"},
        foreignKeys =
        {

                @ForeignKey(
                        entity = LHOficioVersoEntity.class,
                        parentColumns = "versoId",
                        childColumns = "versoFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LHOficioVersoJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "grupoId")
    public Integer grupoId;

    @NonNull
    @ColumnInfo(name = "versoFK")
    public Integer versoFK;

    public int getResponsorioFK() {
        return this.versoFK!=null ? versoFK:0;
    }
    public int getGrupoId() {
        return this.grupoId!=null ? grupoId:0;
    }

}

