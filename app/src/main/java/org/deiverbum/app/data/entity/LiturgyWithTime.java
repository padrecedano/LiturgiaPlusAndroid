package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyTime;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LiturgyWithTime {
    @Embedded
    public LiturgyEntity liturgyEntity;

    @Relation(
            parentColumn = "timeFK",
            entityColumn = "timeID",
            entity = LiturgiaTiempoEntity.class
    )
    public LiturgiaTiempoEntity tiempo;

    public Liturgy getDomainModel() {
        Liturgy dm=new Liturgy();
        dm.setLiturgyID(liturgyEntity.liturgiaId);
        dm.setLiturgiaId(liturgyEntity.getLiturgiaId());
        dm.setSemana(liturgyEntity.getSemana());
        dm.setDia(liturgyEntity.getDia());
        dm.setColorId(liturgyEntity.getColorFK());
        dm.setNombre(liturgyEntity.getNombre());
        LiturgyTime t=new LiturgyTime();
        t.setTiempoId(tiempo.getTiempoId());
        t.setTiempo(tiempo.getTiempo());
        t.setLiturgyName(tiempo.getLiturgyName());
        dm.setLiturgiaTiempo(t);
        return dm;
    }


}
