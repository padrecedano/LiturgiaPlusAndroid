package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Santo;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class SantoWithAll {
    @Embedded
    public SantoEntity santo;
    @Relation(
            parentColumn = "saintID",
            entityColumn = "saintID",
            entity = SantoEntity.class
    )

    public SantoEntity lhSanto;

    //TODO método getVida ¿?
    public Santo getDomainModelLH(){
        Santo dm=new Santo();
        dm.setNombre(santo.nombre);
        dm.setVida("lhSanto.getVida()");
        return dm;
    }

}
