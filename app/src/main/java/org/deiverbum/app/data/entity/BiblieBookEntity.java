package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.BIBLE_BOOK;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.BibleBook;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = BIBLE_BOOK)

public class BiblieBookEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "bookID")
    public Integer libroId = 0;

    @NonNull
    @ColumnInfo(name = "bookType")
    public Integer bookType = 0;

    @NonNull
    @ColumnInfo(name = "shortName")
    public String shortName = "";

    @NonNull
    @ColumnInfo(name = "longName")
    public String longName = "";

    @NonNull
    @ColumnInfo(name = "liturgyName")
    public String liturgyName = "";

    @NonNull
    @ColumnInfo(name = "orderName")
    public String orderName = "";

    @NonNull
    public String getShortName() {
        return shortName;
    }

    @NonNull
    public String getLongName() {
        return longName;
    }

    @NonNull
    public String getLiturgyName() {
        return liturgyName;
    }

    public BibleBook getDomainModel() {
        BibleBook theModel = new BibleBook();
        theModel.setName(getLongName());
        theModel.setLiturgyName(getLiturgyName());
        theModel.setShortName(getShortName());
        return theModel;
    }

}

