package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.SaintLife;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class SaintLifeWithAll {
    @Embedded
    public SaintEntity santo;
    @Relation(
            parentColumn = "saintID",
            entityColumn = "saintFK",
            entity = SaintLifeEntity.class
    )

    public SaintLife saintLife;

    public SaintLife getDomainModel(){
        if(saintLife!=null){
        SaintLife dm=new SaintLife();
        //dm.setSaintFK(saintLife.getSaintFK());
        dm.setShortLife(saintLife.getShortLife());
        dm.longLife=saintLife.getLongLife();
        dm.setMartyrology(saintLife.getMartyrology());
        dm.setSource(saintLife.getSource());
        dm.setDia(String.valueOf(santo.dia));
        dm.setMes(String.valueOf(santo.mes));
        dm.setName(santo.getTheName());
        return dm;}else{
            return null;
        }
    }

}
