package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.data.entity.LiturgyHomilyJoinEntity;
import org.deiverbum.app.model.HomilyList;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
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

    public HomilyList getDomainModel() {
        HomilyList dm = homilia.getDomainModel();
        dm.setTema(joinEntity.getTema());
        return dm;
    }

}
