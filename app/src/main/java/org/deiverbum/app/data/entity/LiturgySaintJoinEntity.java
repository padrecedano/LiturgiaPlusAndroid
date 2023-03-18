package org.deiverbum.app.data.entity;
import static org.deiverbum.app.utils.Constants.LITURGY_SAINT_JOIN;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LITURGY_SAINT_JOIN,
        indices={
                @Index(value={"saintFK"},unique = true)},

        foreignKeys =
        {
                @ForeignKey(
                        entity = LiturgyEntity.class,
                        parentColumns = "liturgyID",
                        childColumns = "liturgyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = SaintEntity.class,
                        parentColumns = "saintID",
                        childColumns = "saintFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class LiturgySaintJoinEntity {

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "liturgyFK")
    public Integer liturgyFK=0;

    @NonNull
    @ColumnInfo(name = "saintFK")
    public Integer saintFK=0;

    @SuppressWarnings("unused")
    public int getLiturgyFK() {
        return liturgyFK;
    }

    @SuppressWarnings("unused")
    public int getSaintFK() {
        return saintFK;
    }

}

