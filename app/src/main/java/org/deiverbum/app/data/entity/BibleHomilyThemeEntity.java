package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.BIBLE_HOMILY_THEME;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = BIBLE_HOMILY_THEME,
        primaryKeys = {"homilyFK"},
        foreignKeys =
        {
                @ForeignKey(
                        entity = HomilyEntity.class,
                        parentColumns = "homilyID",
                        childColumns = "homilyFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        }
)
public class BibleHomilyThemeEntity {

    @NonNull
    @ColumnInfo(name = "homilyFK")
    public Integer homilyFK=0;

    @ColumnInfo(name = "theological", defaultValue = "")
    public String theological="";

    @ColumnInfo(name = "biblical", defaultValue = "")
    public String biblical="";

    @ColumnInfo(name = "reference", defaultValue = "")
    public String reference="";

    public void setHomilyFK(@NonNull Integer homilyFK) {
        this.homilyFK = homilyFK;
    }

    public String getTheological() {
        return theological;
    }

    public void setTheological(String theological) {
        this.theological = theological;
    }

    public String getBiblical() {
        return biblical;
    }

    public void setBiblical(String biblical) {
        this.biblical = biblical;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getHomilyFK() {
        return homilyFK;
    }

}

