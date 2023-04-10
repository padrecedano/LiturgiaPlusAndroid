package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_RESPONSORY_SHORT;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.LHResponsoryShort;
import org.deiverbum.app.utils.Utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_RESPONSORY_SHORT,
        indices = {
                @Index(value = {"text"}, unique = true)}
)
public class LHResponsoryShortEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "responsoryID")
    public Integer responsorioId = 0;

    @NonNull
    @ColumnInfo(name = "text")
    public String texto = "";

    @NonNull
    @ColumnInfo(name = "type")
    public Integer tipo = 0;

    @NonNull
    public String getTexto() {
        return texto;
    }

    @NonNull
    public Integer getTipo() {
        return tipo;
    }


    public LHResponsoryShort getDomainModel(Integer timeId) {
        LHResponsoryShort theModel = new LHResponsoryShort();
        theModel.setResponsoryID(responsorioId);
        theModel.setText(Utils.replaceByTime(getTexto(), timeId));
        theModel.setType(getTipo());
        return theModel;
    }

}

