package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.PaterEntity;
import org.deiverbum.app.data.entity.PaterOpusEntity;
import org.deiverbum.app.model.PaterOpus;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class PaterOpusAll {
    @Embedded
    public PaterOpusEntity paterOpusEntity;
    @Relation(
            parentColumn = "paterFK",
            entityColumn = "paterID",
            entity = PaterEntity.class
    )
    public PaterEntity paterEntity;

    public String getPaterEntity() {
        return paterEntity.getLiturgyName();
    }

    public PaterOpus getDomainModel() {
        PaterOpus dm = new PaterOpus();
        dm.setPater(paterEntity.getDomainModel());
        dm.opusName = paterOpusEntity.opusName;
        dm.liturgyName = paterOpusEntity.liturgyName;
        return dm;
    }
}
