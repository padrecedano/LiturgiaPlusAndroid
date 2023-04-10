package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LITURGY_COLOR;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LITURGY_COLOR)

public class LiturgyColorEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "colorID")
    public Integer colorID = 0;

    @NonNull
    @ColumnInfo(name = "colorName")
    public String colorName = "";

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

