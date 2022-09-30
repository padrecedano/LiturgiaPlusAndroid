package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LITURGY_HOMILY_JOIN;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = LITURGY_HOMILY_JOIN,
        primaryKeys = {"liturgyFK","homilyFK"},
        foreignKeys =
        {
                @ForeignKey(
                        entity = LiturgyEntity.class,
                        parentColumns = "liturgyID",
                        childColumns = "liturgyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = HomilyEntity.class,
                        parentColumns = "homilyID",
                        childColumns = "homilyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LiturgyHomilyJoinEntity {

    @NonNull
    @ColumnInfo(name = "liturgyFK")
    public Integer liturgiaFK=0;

    @NonNull
    @ColumnInfo(name = "homilyFK")
    public Integer homiliaFK=0;

    @NonNull
    @ColumnInfo(name = "theme", defaultValue = "")
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

