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

@Entity(tableName = "lh_biblica_breve_join",
        foreignKeys =
                {
                   @ForeignKey(
                           entity = LHBiblicaBreveEntity.class,
                           parentColumns = "lecturaId",
                           childColumns = "lecturaFK",
                           onDelete = ForeignKey.CASCADE,
                           onUpdate = ForeignKey.CASCADE),
                   @ForeignKey(
                           entity = LHResponsorioBreveEntity.class,
                           parentColumns = "responsorioId",
                           childColumns = "responsorioFK",
                           onDelete = ForeignKey.CASCADE,
                           onUpdate = ForeignKey.CASCADE)
                }
        //indices = {@Index(value = {"grupoFK","salmoFK"}, unique = true)}
        //primaryKeys = {"grupoFK","salmoFK"},
)
public class LHBiblicaBreveJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "grupoId")
    public Integer grupoId;

    @NonNull
    @ColumnInfo(name = "lecturaFK")
    public Integer lecturaFK;

    @NonNull
    @ColumnInfo(name = "responsorioFK")
    public Integer responsorioFK;


    public int getGrupoId() {
        return this.grupoId!=null ? grupoId:0;
    }

}

