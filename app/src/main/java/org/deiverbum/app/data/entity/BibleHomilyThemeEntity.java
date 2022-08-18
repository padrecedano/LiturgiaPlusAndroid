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

@Entity(tableName = "bible_homily_theme",
        //indices = {@Index(value = {"readingFK","homilyFK"}, unique = true)},
        primaryKeys = {"homilyFK"},
        foreignKeys =
        {

                @ForeignKey(
                        entity = HomiliaEntity.class,
                        parentColumns = "homiliaId",
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

