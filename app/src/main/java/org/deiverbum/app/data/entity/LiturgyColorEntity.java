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

@Entity(tableName = "liturgy_color"/*,
        indices = {@Index(value = {"time"}, unique = true)}*/
)
public class LiturgyColorEntity {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "colorID")
    public Integer colorID=0;

    @NonNull
    @ColumnInfo(name = "colorName")
    public String colorName="";

    @NonNull
    public Integer getColorID() {
        return colorID;
    }

    public void setColorID(@NonNull Integer colorID) {
        this.colorID = colorID;
    }

    @NonNull
    public String getColorName() {
        return colorName;
    }

    public void setColorName(@NonNull String colorName) {
        this.colorName = colorName;
    }
}

