package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.SAINT_SHORT_LIFE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.SaintLife;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(
        tableName = SAINT_SHORT_LIFE,
        foreignKeys =
                {
                        @ForeignKey(
                                entity = SaintEntity.class,
                                parentColumns = "saintID",
                                childColumns = "saintFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)
                }
)

public class SaintShortLifeEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "saintFK")
    public Integer saintFK=0;

    @NonNull
    @ColumnInfo(name = "shortLife", defaultValue = "")
    public String shortLife="";

    @NonNull
    public Integer getSaintFK() {
        return saintFK;
    }

    public void setSaintFK(@NonNull Integer saintFK) {
        this.saintFK = saintFK;
    }

    @NonNull
    public String getShortLife() {
        return shortLife;
    }

    public void setShortLife(@NonNull String shortLife) {
        this.shortLife = shortLife;
    }

    public SaintLife getDomainModel(){
        SaintLife theModel=new SaintLife();
        theModel.setShortLife(getShortLife());
        theModel.setSaintFK(getSaintFK());
        return theModel;
    }

}

