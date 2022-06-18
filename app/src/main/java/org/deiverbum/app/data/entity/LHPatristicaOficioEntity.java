package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_patristica_oficio",
        primaryKeys = {"grupoFK","homiliaFK","responsorioFK"},
        /*indices = {@Index(value = {"grupoFK","pericopaFK","responsorioFK"},
                unique = true)},*/

        foreignKeys =
        {
          @ForeignKey(
                    entity = LHPatristicaOficioJoinEntity.class,
                    parentColumns = "grupoId",
                    childColumns = "grupoFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = HomiliaEntity.class,
                    parentColumns = "homiliaId",
                    childColumns = "homiliaFK",
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
public class LHPatristicaOficioEntity {
    @NonNull
    @ColumnInfo(name = "grupoFK")
    public Integer grupoFK=0;

    @NonNull
    @ColumnInfo(name = "homiliaFK")
    public Integer homiliaFK=0;

    @NonNull
    @ColumnInfo(name = "responsorioFK")
    public Integer responsorioFK=0;

    @NonNull
    @ColumnInfo(name = "tema")
    public String tema="";

    @NonNull
    @ColumnInfo(name = "fuente", defaultValue = "")
    public String fuente="";

    @NonNull
    @ColumnInfo(name = "orden", defaultValue= "1")
    public Integer orden=0;
}

