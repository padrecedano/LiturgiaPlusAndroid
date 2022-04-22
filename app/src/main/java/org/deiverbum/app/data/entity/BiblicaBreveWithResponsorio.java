package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgia de las Horas,
 *  desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class BiblicaBreveWithResponsorio {
    @Embedded
    public LHBiblicaBreveEntity lhBiblica;
    @Relation(
            parentColumn = "pericopaFK",
            entityColumn = "pericopaId",
            entity = BibliaLecturaEntity.class
    )
    public LecturaWithLibro bibliaLectura;

    @Relation(
            parentColumn = "responsorioFK",
            entityColumn = "responsorioId",
            entity = LHResponsorioEntity.class
    )
    public LHResponsorioEntity lhResponsorio;

    public BiblicaBreve getDomainModelBreve(Integer timeId){
        BiblicaBreve theModel= bibliaLectura.getDomainModelBreve();
        theModel.setResponsorio(lhResponsorio.getDomainModel(timeId));
        return theModel;
    }

}
