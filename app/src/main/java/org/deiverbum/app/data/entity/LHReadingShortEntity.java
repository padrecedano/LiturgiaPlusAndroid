package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_READING_SHORT;

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

@Entity(tableName = LH_READING_SHORT,
        indices = {@Index(value = {"text"}, unique = true)}
)
public class LHReadingShortEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "readingID")
    public Integer lecturaId=0;

    @NonNull
    @ColumnInfo(name = "text")
    public String texto="";

    @NonNull
    @ColumnInfo(name = "quote")
    public String cita="";


}

