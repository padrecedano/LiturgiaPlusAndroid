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

@Entity(tableName = "lh_biblica_oficio",
        primaryKeys = {"grupoFK","lecturaFK","responsorioFK"},
        /*indices = {@Index(value = {"grupoFK","pericopaFK","responsorioFK"},
                unique = true)},*/

        foreignKeys =
        {
                @ForeignKey(
                        entity = LHBiblicaOficioJoinEntity.class,
                        parentColumns = "grupoId",
                        childColumns = "grupoFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = BibliaLecturaEntity.class,
                    parentColumns = "lecturaId",
                    childColumns = "lecturaFK",
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
public class LHBiblicaOficioEntity {
    @NonNull
    //@PrimaryKey
    @ColumnInfo(name = "grupoFK")
    public Integer grupoFK=0;

    @NonNull
    @ColumnInfo(name = "lecturaFK")
    public Integer lecturaFK=0;

    @NonNull
    @ColumnInfo(name = "responsorioFK")
    public Integer responsorioFK=0;

    @NonNull
    @ColumnInfo(name = "tema")
    public String tema="";

    @NonNull
    @ColumnInfo(name = "orden", defaultValue= "1")
    public Integer orden=0;
}

