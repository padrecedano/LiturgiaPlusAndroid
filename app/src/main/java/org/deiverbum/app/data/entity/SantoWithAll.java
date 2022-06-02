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
            parentColumn = "santoId",
            entityColumn = "santoId",
            entity = LHSantoEntity.class
    )

    public LHSantoEntity lhSanto;

    public Santo getDomainModel(){
        Santo dm=new Santo();
        dm.setNombre(santo.nombre);
        dm.setVida(lhSanto.getVida());
        return dm;
    }


}
