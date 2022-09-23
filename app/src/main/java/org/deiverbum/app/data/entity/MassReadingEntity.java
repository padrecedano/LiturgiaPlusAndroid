package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.deiverbum.app.model.MassReading;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "mass_reading",
        primaryKeys = {"liturgyFK","readingFK","theOrder"},
        foreignKeys =
                {
                        @ForeignKey(
                                entity = LiturgyEntity.class,
                                parentColumns = "liturgyID",
                                childColumns = "liturgyFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE),
                       @ForeignKey(
                                entity = BibleReadingEntity.class,
                                parentColumns = "readingID",
                                childColumns = "readingFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)}/*,
         indices = {@Index(value = {"liturgiaId","pericopaFK","orden"},unique
          = true)}*/
)
public class MassReadingEntity {
    @NonNull
    @ColumnInfo(name = "liturgyFK")
    public Integer groupFK=0;

    @NonNull
    @ColumnInfo(name = "readingFK")
    public Integer readingFK=0;

    @NonNull
    @ColumnInfo(name = "theOrder")
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

    public MassReading getDomainModel() {
        MassReading theModel=new MassReading();
        theModel.setTema(getTema());
        theModel.setOrden(getOrden());
        return theModel;
    }
}

