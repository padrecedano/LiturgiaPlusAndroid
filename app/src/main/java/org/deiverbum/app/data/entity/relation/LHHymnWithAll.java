package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHHymnEntity;
import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.model.LHHymn;

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

    public LHHymn getDomainModel(){
        LHHymn dm=new LHHymn();
        dm.setTexto(himno.getHimno());
        return dm;
    }
}
