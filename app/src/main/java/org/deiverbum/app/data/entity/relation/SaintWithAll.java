package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.SaintEntity;
import org.deiverbum.app.data.entity.SaintLifeEntity;
import org.deiverbum.app.model.Saint;

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

    public SaintLifeEntity lhSanto;

    public Saint getDomainModelLH() {
        Saint dm = new Saint();
        dm.setTheName(santo.nombre);
        return dm;
    }

}
