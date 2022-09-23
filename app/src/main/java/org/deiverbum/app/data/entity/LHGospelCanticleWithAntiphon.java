package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.LHGospelCanticle_;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHGospelCanticleWithAntiphon {
    @Embedded
    public LHGospelCanticleEntity ce;
    @Relation(
            parentColumn = "antiphonFK",
            entityColumn = "antiphonID",
            entity = AntiphonEntity.class
    )
    public AntiphonEntity antifona;

    public String getAntifona() {
        return antifona.getAntifona();
    }

    public LHGospelCanticle_ getDomainModel(Integer tipo) {
        LHGospelCanticle_ ce=new LHGospelCanticle_();
        ce.setTipo(tipo);
        ce.setAntifona(getAntifona());
        return ce;
    }
}
