package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LiturgySaintJoinEntity;
import org.deiverbum.app.data.entity.SaintEntity;
import org.deiverbum.app.data.entity.SaintLifeEntity;
import org.deiverbum.app.data.entity.SaintShortLifeEntity;
import org.deiverbum.app.model.Saint;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class SaintShortWithAll {
    @Embedded
    public LiturgySaintJoinEntity liturgyEntity;

    @Relation(
            parentColumn = "saintFK",
            entityColumn = "saintFK",
            entity = SaintShortLifeEntity.class
    )
    public SaintShortLifeEntity shortLife;

    @Relation(
            parentColumn = "saintFK",
            entityColumn = "saintID",
            entity = SaintEntity.class
    )
    public SaintEntity saint;

    @Relation(
            parentColumn = "saintFK",
            entityColumn = "saintFK",
            entity = SaintLifeEntity.class
    )
    public SaintLifeEntity longLife;

    public Saint getDomainModel() {
        Saint dm=new Saint();
        String saintLife;
        if(shortLife != null){
            saintLife =shortLife.getShortLife();
        }else{
            saintLife=longLife.getMartyrology();
        }
        dm.setVida(saintLife);
        dm.setTheName(saint.getTheName());
        return dm;
    }


}
