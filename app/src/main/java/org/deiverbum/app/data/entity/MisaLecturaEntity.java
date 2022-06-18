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
        primaryKeys = {"liturgiaFK","lecturaFK","orden"},
        foreignKeys =
                {
                        @ForeignKey(
                                entity = LiturgiaEntity.class,
                                parentColumns = "liturgiaId",
                                childColumns = "liturgiaFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE),
                       @ForeignKey(
                                entity = BibliaLecturaEntity.class,
                                parentColumns = "lecturaId",
                                childColumns = "lecturaFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)}/*,
         indices = {@Index(value = {"liturgiaId","pericopaFK","orden"},unique
          = true)}*/
)
public class MisaLecturaEntity {
    @NonNull
    @ColumnInfo(name = "liturgiaFK")
    public Integer homiliaId=0;

    @NonNull
    @ColumnInfo(name = "lecturaFK")
    public Integer lecturaFK=0;

    @NonNull
    @ColumnInfo(name = "orden")
    public Integer orden=0;

    @NonNull
    @ColumnInfo(name = "tema")
    public String tema="";

    public Integer getOrden() {
        return orden;
    }

    public String getTema() {
        return tema;
    }
}

