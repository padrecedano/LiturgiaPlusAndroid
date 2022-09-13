package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Homilia;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LiturgiaWithHomilias {
    @Embedded
    public LiturgyHomilyJoinEntity joinEntity;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyID",
            entity = HomilyEntity.class
    )
    public HomilyAll homilia;

    public Homilia getDomainModel() {
        Homilia dm=homilia.getDomainModel();
        dm.setTema(joinEntity.getTema());
        return dm;
    }

}
