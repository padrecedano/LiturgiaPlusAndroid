package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.HOMILY;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = HOMILY,
        foreignKeys =
                {
                        @ForeignKey(
                                entity = PaterOpusEntity.class,
                                parentColumns = "opusID",
                                childColumns = "opusFK",
                                onDelete = ForeignKey.SET_DEFAULT,
                                onUpdate = ForeignKey.SET_DEFAULT)},
         indices = {@Index(value = {"opusFK", "date", "book", "chapter", "number", "paragraph", "collectionFK", "colNumber", "colParagraph"},unique = true)}
)

public class HomilyEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "homilyID")
    public Integer homiliaId=0;

    @NonNull
    @ColumnInfo(name = "opusFK", defaultValue = "0")
    public Integer obraFK=0;

    @NonNull
    @ColumnInfo(name = "date", defaultValue = "0")
    public Integer fecha=0;

    @NonNull
    @ColumnInfo(name = "book", defaultValue = "0")
    public Integer libro=0;

    @NonNull
    @ColumnInfo(name = "chapter", defaultValue = "0")
    public Integer capitulo=0;

    @NonNull
    @ColumnInfo(name = "number", defaultValue = "0")
    public Integer numero=0;

    @NonNull
    @ColumnInfo(name = "paragraph", defaultValue = "0")
    public Integer parrafo=0;

    @NonNull
    @ColumnInfo(name = "collectionFK", defaultValue = "0")
    public Integer coleccionFK=0;

    @NonNull
    @ColumnInfo(name = "colNumber", defaultValue = "0")
    public Integer colDoc=0;

    @NonNull
    @ColumnInfo(name = "colParagraph", defaultValue = "0")
    public Integer colParrafo=0;

    @NonNull
    @ColumnInfo(name = "homily")
    public String texto="";

    @NonNull
    public Integer getNumero() {
        return numero;
    }

    @NonNull
    public String getTexto() {
        return texto;
    }
}

