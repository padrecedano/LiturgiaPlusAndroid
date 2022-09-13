package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.CanticoEvangelico;

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

    public CanticoEvangelico getDomainModel(Integer tipo) {
        CanticoEvangelico ce=new CanticoEvangelico();
        ce.setTipo(tipo);
        ce.setAntifona(getAntifona());
        return ce;
    }
}
