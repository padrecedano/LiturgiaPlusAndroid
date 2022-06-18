package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
    public Integer homiliaId=0;

    @NonNull
    @ColumnInfo(name = "obraFK", defaultValue = "0")
    public Integer obraFK=0;

    @NonNull
    @ColumnInfo(name = "fecha", defaultValue = "0")
    public Integer fecha=0;

    @NonNull
    @ColumnInfo(name = "libro", defaultValue = "0")
    public Integer libro=0;

    @NonNull
    @ColumnInfo(name = "capitulo", defaultValue = "0")
    public Integer capitulo=0;

    @NonNull
    @ColumnInfo(name = "numero", defaultValue = "0")
    public Integer numero=0;

    @NonNull
    @ColumnInfo(name = "parrafo", defaultValue = "0")
    public Integer parrafo=0;

    @NonNull
    @ColumnInfo(name = "coleccionFK", defaultValue = "0")
    public Integer coleccionFK=0;

    @NonNull
    @ColumnInfo(name = "colDoc", defaultValue = "0")
    public Integer colDoc=0;

    @NonNull
    @ColumnInfo(name = "colParrafo", defaultValue = "0")
    public Integer colParrafo=0;

    @NonNull
    @ColumnInfo(name = "homilia")
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

