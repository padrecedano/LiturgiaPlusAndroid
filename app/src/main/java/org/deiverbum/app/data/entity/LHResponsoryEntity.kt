package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_RESPONSORY;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.LHResponsory;
import org.deiverbum.app.utils.Utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_RESPONSORY/*,
        indices={
                @Index(value={"texto","fuente", "tipo"},unique = true)}*/
)
public class LHResponsoryEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "responsoryID")
    public Integer responsorioId = 0;
    @NonNull
    @ColumnInfo(name = "text")
    public String texto = "";
    @NonNull
    @ColumnInfo(name = "source")
    public String fuente = "";
    @NonNull
    @ColumnInfo(name = "type")
    public Integer tipo = 0;

    @NonNull
    public String getTexto() {
        return texto;
    }

    @NonNull
    public String getFuente() {
        return fuente;
    }

    @NonNull
    public Integer getTipo() {
        return tipo;
    }

    public LHResponsory getDomainModel(Integer timeId) {
        LHResponsory theModel = new LHResponsory();
        theModel.setText(Utils.replaceByTime(getTexto(), timeId));
        theModel.setType(getTipo());
        theModel.setSource(getFuente());
        return theModel;
    }

}

