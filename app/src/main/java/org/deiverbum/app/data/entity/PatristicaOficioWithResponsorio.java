package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Patristica;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgia de las Horas,
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
    public LHResponsorioLargoEntity lhResponsorio;



    public Patristica getDomainModelOficio(Integer tiempoId) {
        Patristica theModel= homilyAll.getPatristicaDomainModel();
        theModel.setTema(lhPatristica.tema);
        theModel.setFuente(lhPatristica.fuente);
        theModel.setOrden(lhPatristica.orden);
        theModel.setResponsorioLargo(lhResponsorio.getDomainModel(tiempoId));
        return theModel;
    }
}
