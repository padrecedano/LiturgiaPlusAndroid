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
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)},
         indices = {@Index(value = {"obraFK","fecha","numero"},unique = true)}
)
public class HomiliaEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "homiliaId")
    public Integer homiliaId;

    @NonNull
    @ColumnInfo(name = "obraFK")
    public Integer obraFK;

    @NonNull
    @ColumnInfo(name = "fecha")
    public Integer fecha;

    @NonNull
    @ColumnInfo(name = "numero")
    public Integer numero;

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

