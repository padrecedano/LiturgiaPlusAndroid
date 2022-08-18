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

@Entity(tableName = "mass_reading",
        primaryKeys = {"groupFK","readingFK","order"},
        foreignKeys =
                {
                        @ForeignKey(
                                entity = LiturgyGroupEntity.class,
                                parentColumns = "groupID",
                                childColumns = "groupFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE),
                       @ForeignKey(
                                entity = BibliaLecturaEntity.class,
                                parentColumns = "lecturaId",
                                childColumns = "readingFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)}/*,
         indices = {@Index(value = {"liturgiaId","pericopaFK","orden"},unique
          = true)}*/
)
public class MassReadingEntity {
    @NonNull
    @ColumnInfo(name = "groupFK")
    public Integer groupFK=0;

    @NonNull
    @ColumnInfo(name = "readingFK")
    public Integer readingFK=0;

    @NonNull
    @ColumnInfo(name = "order")
    public Integer order=0;

    @NonNull
    @ColumnInfo(name = "theme")
    public String theme="";

    public Integer getOrden() {
        return order;
    }

    public String getTema() {
        return theme;
    }
}

