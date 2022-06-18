package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.CanticoEvangelico;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class CanticoEvangelicoWithAntifona {
    @Embedded
    public LHCanticoEvangelicoEntity ce;
    @Relation(
            parentColumn = "antifonaFK",
            entityColumn = "antifonaId",
            entity = AntifonaEntity.class
    )
    public AntifonaEntity antifona;

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
