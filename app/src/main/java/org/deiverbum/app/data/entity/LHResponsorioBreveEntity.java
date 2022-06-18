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

@Entity(tableName = "lh_responsorio_breve",
        indices={
                @Index(value={"texto"},unique = true)}
)
public class LHResponsorioBreveEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "responsorioId")
    public Integer responsorioId=0;

    @NonNull
    @ColumnInfo(name = "texto")
    public String texto="";

    @NonNull
    @ColumnInfo(name = "tipo")
    public Integer tipo=0;

    @NonNull
    public String getTexto() {
        return texto;
    }

    @NonNull
    public Integer getTipo() {
        return tipo;
    }


    public Responsorio getDomainModel(Integer timeId){
        Responsorio theModel=new Responsorio();
        theModel.setTexto(Utils.replaceByTime(getTexto(),timeId));
        theModel.setForma(getTipo());
        //theModel.setRef(getFuente());
        return theModel;
    }

}

