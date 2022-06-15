package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Patristica;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "homilia",
        foreignKeys =
                {
                        @ForeignKey(
                                entity = ObraEntity.class,
                                parentColumns = "obraId",
                                childColumns = "obraFK",
                                onDelete = ForeignKey.SET_DEFAULT,
                                onUpdate = ForeignKey.SET_DEFAULT)},
         indices = {@Index(value = {"obraFK", "fecha", "libro", "capitulo", "numero", "parrafo", "coleccionFK", "colDoc", "colParrafo"},unique = true)}

)
public class HomiliaEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "homiliaId")
    public Integer homiliaId;

    @NonNull
    @ColumnInfo(name = "obraFK", defaultValue = "0")
    public Integer obraFK;

    @NonNull
    @ColumnInfo(name = "fecha", defaultValue = "0")
    public Integer fecha;

    @NonNull
    @ColumnInfo(name = "libro", defaultValue = "0")
    public Integer libro;

    @NonNull
    @ColumnInfo(name = "capitulo", defaultValue = "0")
    public Integer capitulo;

    @NonNull
    @ColumnInfo(name = "numero", defaultValue = "0")
    public Integer numero;

    @NonNull
    @ColumnInfo(name = "parrafo", defaultValue = "0")
    public Integer parrafo;

    @NonNull
    @ColumnInfo(name = "coleccionFK", defaultValue = "0")
    public Integer coleccionFK;

    @NonNull
    @ColumnInfo(name = "colDoc", defaultValue = "0")
    public Integer colDoc;

    @NonNull
    @ColumnInfo(name = "colParrafo", defaultValue = "0")
    public Integer colParrafo;

    @NonNull
    @ColumnInfo(name = "homilia")
    public String texto;

    public Integer getNumero() {
        return numero !=null ? numero : 0;
    }

    public String getTexto() {
        return texto!=null ? texto : "";
    }
}

