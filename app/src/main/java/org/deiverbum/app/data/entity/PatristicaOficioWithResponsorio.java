package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaOficio;
import org.deiverbum.app.model.Homilia;
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
    public LHPatristicaOficioEntity lhPatristica;

    @Relation(
            parentColumn = "homiliaFK",
            entityColumn = "homiliaId",
            entity = HomiliaEntity.class
    )
    public HomiliaWithAll homiliaWithAll;

    @Relation(
            parentColumn = "responsorioFK",
            entityColumn = "responsorioId",
            entity = LHResponsorioEntity.class
    )
    public LHResponsorioLargoEntity lhResponsorio;



    public Patristica getDomainModelOficio(Integer tiempoId) {
        Patristica theModel= homiliaWithAll.getPatristicaDomainModel();
        theModel.setTema(lhPatristica.tema);
        theModel.setFuente(lhPatristica.fuente);
        theModel.setOrden(lhPatristica.orden);
        theModel.setResponsorioLargo(lhResponsorio.getDomainModel(tiempoId));
        return theModel;
    }
}
