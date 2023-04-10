package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.SAINT_LIFE;

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
        tableName = SAINT_LIFE,
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

public class SaintLifeEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "saintFK")
    public Integer saintFK = 0;

    @NonNull
    @ColumnInfo(name = "longLife")
    public String longLife = "";

    @NonNull
    @ColumnInfo(name = "martyrology")
    public String martyrology = "";

    @NonNull
    @ColumnInfo(name = "theSource")
    public String theSource = "";

    @NonNull
    public Integer getSaintFK() {
        return saintFK;
    }

    public void setSaintFK(@NonNull Integer saintFK) {
        this.saintFK = saintFK;
    }

    @NonNull
    public String getLongLife() {
        return longLife;
    }

    public void setLongLife(@NonNull String longLife) {
        this.longLife = longLife;
    }

    @NonNull
    public String getMartyrology() {
        return martyrology;
    }

    public void setMartyrology(@NonNull String martyrology) {
        this.martyrology = martyrology;
    }

    @NonNull
    public String getTheSource() {
        return theSource;
    }

    public void setTheSource(@NonNull String theSource) {
        this.theSource = theSource;
    }

    public SaintLife getDomainModel() {
        SaintLife theModel = new SaintLife();
        theModel.setLongLife(getLongLife());
        theModel.setSaintFK(getSaintFK());
        theModel.setMartyrology(getMartyrology());
        theModel.setTheSource(getTheSource());
        return theModel;
    }

}

