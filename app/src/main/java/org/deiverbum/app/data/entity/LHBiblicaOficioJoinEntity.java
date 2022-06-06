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

@Entity(tableName = "lh_biblica_oficio_join"
        //indices = {@Index(value = {"grupoFK","salmoFK"}, unique = true)}
        //primaryKeys = {"grupoFK","salmoFK"},
)
public class LHBiblicaOficioJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "grupoId")
    public Integer grupoId;
/*
    @NonNull
    @ColumnInfo(name = "antifonaFK")
    public Integer antifonaFK;

    @NonNull
    @ColumnInfo(name = "casoFK")
    public Integer casoFK;

    public int getSalmoFK() {
        return this.antifonaFK!=null ? antifonaFK:0;
    }
    public int getGrupoId() {
        return this.grupoId!=null ? grupoId:0;
    }
*/
}

