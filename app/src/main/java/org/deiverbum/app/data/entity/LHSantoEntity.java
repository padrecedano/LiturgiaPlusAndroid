package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(
        tableName = "lh_santo"/*,
        indices = {@Index(value = {"nombre"},unique = true)}*/
)
public class LHSantoEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "santoId")
    public Integer santoId=0;

    @NonNull
    @ColumnInfo(name = "vida")
    public String vida="";

    public void setVida(@NonNull String vida) {
        this.vida = vida;
    }

    @NonNull
    public String getVida() {
        return vida;
    }

}

