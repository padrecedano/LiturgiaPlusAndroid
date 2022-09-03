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

@Entity(tableName = "lh_reading_short_join",
        foreignKeys =
                {
                   @ForeignKey(
                           entity = LHBiblicaBreveEntity.class,
                           parentColumns = "readingID",
                           childColumns = "readingFK",
                           onDelete = ForeignKey.CASCADE,
                           onUpdate = ForeignKey.CASCADE),
                   @ForeignKey(
                           entity = LHResponsorioBreveEntity.class,
                           parentColumns = "responsoryID",
                           childColumns = "responsoryFK",
                           onDelete = ForeignKey.CASCADE,
                           onUpdate = ForeignKey.CASCADE)
                }
        //indices = {@Index(value = {"grupoFK","salmoFK"}, unique = true)}
        //primaryKeys = {"grupoFK","salmoFK"},
)
public class LHBiblicaBreveJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId=0;

    @NonNull
    @ColumnInfo(name = "readingFK")
    public Integer lecturaFK=0;

    @NonNull
    @ColumnInfo(name = "responsoryFK")
    public Integer responsorioFK=0;


    public int getGrupoId() {
        return  grupoId;
    }

}

