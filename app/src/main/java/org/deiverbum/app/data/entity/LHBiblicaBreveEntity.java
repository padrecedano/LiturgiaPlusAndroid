package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_reading_short",
        indices = {@Index(value = {"text"}, unique = true)}
/*
        foreignKeys =
        {
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
        }*/
)
public class LHBiblicaBreveEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "readingID")
    public Integer lecturaId=0;

    @NonNull
    @ColumnInfo(name = "text")
    public String texto="";

    @NonNull
    @ColumnInfo(name = "quote")
    public String cita="";


}

