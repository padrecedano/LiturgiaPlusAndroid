package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaOficio;

import java.util.ArrayList;
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
public class BiblicaOficioAll {
    @Embedded
    public LHBiblicaOficioJoinEntity lhBiblica;

    @Relation(
            parentColumn = "grupoId",
            entityColumn = "grupoFK",
            entity = LHBiblicaOficioEntity.class
    )
    //public BiblicaOficioWithResponsorio biblica;
    public List<BiblicaOficioWithResponsorio> biblica;

    /*
    @Relation(
            parentColumn = "pericopaFK",
            entityColumn = "pericopaId",
            entity = BibliaLecturaEntity.class
    )
    public BibliaLecturaEntity bibliaLectura;
*/
    public List<BiblicaOficio> getDomainModel(Integer tiempoId) {
        final List<BiblicaOficio> theList = new ArrayList<>();

        for (BiblicaOficioWithResponsorio item : biblica) {
            theList.add(item.getDomainModel(tiempoId));
        }
        return theList;
        //BiblicaOficio theModel= biblica.getDomainModelOficio();
        /*theModel.setTema(lhBiblica.tema);
        theModel.setOrden(lhBiblica.orden);
        theModel.setResponsorioLargo(lhResponsorio.getDomainModel(tiempoId));*/
        //return theModel;
    }

}
