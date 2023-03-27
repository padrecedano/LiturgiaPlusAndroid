package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHAntiphonEntity;
import org.deiverbum.app.data.entity.LHGospelCanticleEntity;
import org.deiverbum.app.model.LHGospelCanticle;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class LHGospelCanticleWithAntiphon {
    @Embedded
    public LHGospelCanticleEntity ce;
    @Relation(
            parentColumn = "antiphonFK",
            entityColumn = "antiphonID",
            entity = LHAntiphonEntity.class
    )
    public LHAntiphonEntity antifona;

    public String getAntifona() {
        return antifona.getAntifona();
    }

    public LHGospelCanticle getDomainModel(Integer tipo) {
        LHGospelCanticle ce=new LHGospelCanticle();
        ce.setTypeID(tipo);
        ce.setAntiphon(getAntifona());
        return ce;
    }
}
