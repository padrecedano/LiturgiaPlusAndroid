package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Himno;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class HimnoWithAll {
    @Embedded
    public LHHimnoJoinEntity himnoJoin;
    @Relation(
            parentColumn = "hymnFK",
            entityColumn = "hymnID",
            entity = HimnoEntity.class
    )
    public HimnoEntity himno;

    public Himno getDomainModel(){
        Himno dm=new Himno();
        dm.setTexto(himno.getHimno());
        return dm;
    }
}
