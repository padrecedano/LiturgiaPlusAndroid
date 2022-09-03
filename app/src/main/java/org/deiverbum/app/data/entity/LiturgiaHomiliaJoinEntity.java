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

@Entity(tableName = "liturgy_homily_join",
        //indices = {@Index(value = {"grupoFK","salmoFK"}, unique = true)}
        primaryKeys = {"liturgyFK","homilyFK"},
        foreignKeys =
        {
                @ForeignKey(
                        entity = LiturgiaEntity.class,
                        parentColumns = "liturgyID",
                        childColumns = "liturgyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = HomiliaEntity.class,
                        parentColumns = "homilyID",
                        childColumns = "homilyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LiturgiaHomiliaJoinEntity {

    @NonNull
    @ColumnInfo(name = "liturgyFK")
    public Integer liturgiaFK=0;

    @NonNull
    @ColumnInfo(name = "homilyFK")
    public Integer homiliaFK=0;

    @NonNull
    @ColumnInfo(name = "theme")
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

