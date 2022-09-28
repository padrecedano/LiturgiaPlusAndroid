package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.LHGospelCanticle;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHGospelCanticleWithAntiphon {
    @Embedded
    public LHGospelCanticleJoinEntity ce;
    @Relation(
            parentColumn = "antiphonFK",
            entityColumn = "antiphonID",
            entity = AntiphonEntity.class
    )
    public AntiphonEntity antifona;

    public String getAntifona() {
        return antifona.getAntifona();
    }

    public LHGospelCanticle getDomainModel(Integer tipo) {
        LHGospelCanticle ce=new LHGospelCanticle();
        ce.setTipo(tipo);
        ce.setAntifona(getAntifona());
        return ce;
    }
}
