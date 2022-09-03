package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.Santo;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(
        tableName = "saint_life",
        foreignKeys =
                {

                        @ForeignKey(
                                entity = SantoEntity.class,
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
    public Integer saintFK=0;

    @NonNull
    @ColumnInfo(name = "life")
    public String life="";

    @NonNull
    @ColumnInfo(name = "martyrology")
    public String martyrology="";

    @NonNull
    @ColumnInfo(name = "source")
    public String source="";

    @NonNull
    public Integer getSaintFK() {
        return saintFK;
    }

    public void setSaintFK(@NonNull Integer saintFK) {
        this.saintFK = saintFK;
    }

    @NonNull
    public String getLife() {
        return life;
    }

    public void setLife(@NonNull String life) {
        this.life = life;
    }

    @NonNull
    public String getMartyrology() {
        return martyrology;
    }

    public void setMartyrology(@NonNull String martyrology) {
        this.martyrology = martyrology;
    }

    @NonNull
    public String getSource() {
        return source;
    }

    public void setSource(@NonNull String source) {
        this.source = source;
    }

    public SaintLife getDomainModel(){
        SaintLife theModel=new SaintLife();
        theModel.setLife(getLife());
        theModel.setSaintFK(getSaintFK());
        theModel.setMartyrology(getMartyrology());
        theModel.setSource(getSource());
        return theModel;
    }

}

