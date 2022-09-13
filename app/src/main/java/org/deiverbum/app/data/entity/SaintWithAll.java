package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.Santo;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class SaintWithAll {
    @Embedded
    public SaintEntity santo;
    @Relation(
            parentColumn = "saintID",
            entityColumn = "saintFK",
            entity = SaintLifeEntity.class
    )

    public SaintLife lhSanto;

    //TODO método getVida ¿?
    public Santo getDomainModelLH(){
        Santo dm=new Santo();
        dm.setNombre(santo.nombre);
        dm.setVida(lhSanto.getShortLife());
        return dm;
    }

}
