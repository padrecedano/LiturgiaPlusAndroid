package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Himno;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHHymnWithAll {
    @Embedded
    public LHHymnJoinEntity himnoJoin;
    @Relation(
            parentColumn = "hymnFK",
            entityColumn = "hymnID",
            entity = LHHymnEntity.class
    )
    public LHHymnEntity himno;

    public Himno getDomainModel(){
        Himno dm=new Himno();
        dm.setTexto(himno.getHimno());
        return dm;
    }
}
