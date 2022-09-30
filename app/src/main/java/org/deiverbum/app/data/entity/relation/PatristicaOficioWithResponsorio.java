package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.data.entity.LHOfficePatristicEntity;
import org.deiverbum.app.data.entity.LHResponsoryEntity;
import org.deiverbum.app.model.LHOfficePatristic;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgy de las Horas,
 *  desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class PatristicaOficioWithResponsorio {
    @Embedded
    public LHOfficePatristicEntity lhPatristica;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyID",
            entity = HomilyEntity.class
    )
    public HomilyAll homilyAll;

    @Relation(
            parentColumn = "responsoryFK",
            entityColumn = "responsoryID",
            entity = LHResponsoryEntity.class
    )
    public LHResponsoryEntity lhResponsorio;



    public LHOfficePatristic getDomainModelOficio(Integer tiempoId) {
        LHOfficePatristic theModel= homilyAll.getPatristicaDomainModel();
        theModel.setTema(lhPatristica.tema);
        theModel.setFuente(lhPatristica.fuente);
        theModel.setOrden(lhPatristica.orden);
        theModel.setResponsorioLargo(lhResponsorio.getDomainModel(tiempoId));
        return theModel;
    }
}
