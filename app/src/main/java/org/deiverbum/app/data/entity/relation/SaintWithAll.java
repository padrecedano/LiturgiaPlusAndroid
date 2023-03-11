package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.SaintEntity;
import org.deiverbum.app.data.entity.SaintLifeEntity;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.SaintLife;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
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
    public Saint getDomainModelLH(){
        Saint dm=new Saint();
        dm.setTheName(santo.nombre);
        dm.setVida(lhSanto.getShortLife());
        return dm;
    }

}
