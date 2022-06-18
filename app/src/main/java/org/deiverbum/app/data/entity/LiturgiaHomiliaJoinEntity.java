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

@Entity(tableName = "liturgia_homilia_join",
        //indices = {@Index(value = {"grupoFK","salmoFK"}, unique = true)}
        primaryKeys = {"liturgiaFK","homiliaFK"},
        foreignKeys =
        {
                @ForeignKey(
                        entity = LiturgiaEntity.class,
                        parentColumns = "liturgiaId",
                        childColumns = "liturgiaFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = HomiliaEntity.class,
                        parentColumns = "homiliaId",
                        childColumns = "homiliaFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LiturgiaHomiliaJoinEntity {

    @NonNull
    @ColumnInfo(name = "liturgiaFK")
    public Integer liturgiaFK=0;

    @NonNull
    @ColumnInfo(name = "homiliaFK")
    public Integer homiliaFK=0;

    @NonNull
    @ColumnInfo(name = "tema")
    public String tema="";

    @SuppressWarnings("unused")
    public int getLiturgiaFK() {
        return liturgiaFK;
    }

    @SuppressWarnings("unused")
    public int getHomiliaFK() {
        return homiliaFK;
    }
    @NonNull
    public String getTema() {
        return tema;
    }

}

