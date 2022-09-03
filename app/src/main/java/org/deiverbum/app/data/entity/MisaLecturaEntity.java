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

@Entity(tableName = "misa_lectura",
        primaryKeys = {"liturgyFK","readingFK","order"},
        foreignKeys =
                {
                        @ForeignKey(
                                entity = LiturgiaEntity.class,
                                parentColumns = "liturgyID",
                                childColumns = "liturgyFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE),
                       @ForeignKey(
                                entity = BibliaLecturaEntity.class,
                                parentColumns = "readingID",
                                childColumns = "readingFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)}/*,
         indices = {@Index(value = {"liturgiaId","pericopaFK","orden"},unique
          = true)}*/
)
public class MisaLecturaEntity {
    @NonNull
    @ColumnInfo(name = "liturgyFK")
    public Integer homiliaId=0;

    @NonNull
    @ColumnInfo(name = "readingFK")
    public Integer lecturaFK=0;

    @NonNull
    @ColumnInfo(name = "order")
    public Integer orden=0;

    @NonNull
    @ColumnInfo(name = "theme")
    public String tema="";

    public Integer getOrden() {
        return orden;
    }

    public String getTema() {
        return tema;
    }
}

