package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.LiturgiaTiempo;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LiturgiaWithTiempo {
    @Embedded
    public LiturgiaEntity joinEntity;

    @Relation(
            parentColumn = "tiempoFK",
            entityColumn = "tiempoId",
            entity = LiturgiaTiempoEntity.class
    )
    public LiturgiaTiempoEntity tiempo;

    public Liturgia getDomainModel() {
        Liturgia dm=new Liturgia();
        dm.setLiturgiaId(joinEntity.getLiturgiaId());
        dm.setSemana(joinEntity.getSemana());
        dm.setDia(joinEntity.getDia());
        dm.setColorId(joinEntity.getColorFK());
        dm.setNombre(joinEntity.getNombre());
        LiturgiaTiempo t=new LiturgiaTiempo();
        t.setTiempoId(tiempo.getTiempoId());
        t.setTiempo(tiempo.getTiempo());
        t.setLiturgyName(tiempo.getLiturgyName());
        dm.setLiturgiaTiempo(t);
        return dm;
    }

}
