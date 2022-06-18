package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Responsorio;
import org.deiverbum.app.utils.Utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_responsorio",
        indices={
                @Index(value={"texto","fuente", "tipo"},unique = true)}
)
public class LHResponsorioEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "responsorioId")
    public Integer responsorioId=0;

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

    @NonNull
    @ColumnInfo(name = "texto")
    public String texto="";

    @NonNull
    @ColumnInfo(name = "fuente")
    public String fuente="";

    @NonNull
    @ColumnInfo(name = "tipo")
    public Integer tipo=0;

    public Responsorio getDomainModel(Integer timeId){
        Responsorio theModel=new Responsorio();
        theModel.setTexto(Utils.replaceByTime(getTexto(),timeId));
        theModel.setForma(getTipo());
        //theModel.setRef(getFuente());
        return theModel;
    }

}

