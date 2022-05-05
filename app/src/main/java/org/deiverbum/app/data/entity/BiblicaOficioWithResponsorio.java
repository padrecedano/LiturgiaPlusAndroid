package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.BiblicaOficio;

import java.util.List;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgia de las Horas,
 *  desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class BiblicaOficioWithResponsorio {
    @Embedded
    public LHBiblicaOficioEntity lhBiblica;

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

    public Biblica getDomainModel(Integer timeId){
        Biblica theModel= bibliaLectura.getDomainModel();
        //theModel.setTema(lhBiblica.tema);
        //theModel.setResponsorio(lhResponsorio.getDomainModel(timeId));
        return theModel;
    }

    public BiblicaOficio getDomainModelOficio(Integer tiempoId) {
        BiblicaOficio theModel= bibliaLectura.getDomainModelOficio();
        theModel.setTema(lhBiblica.tema);
        theModel.setOrden(lhBiblica.orden);
        theModel.setResponsorio(lhResponsorio.getDomainModel(tiempoId));
        return theModel;
    }
}
