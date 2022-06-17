package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
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
public class BiblicaBreveAll {
    @Embedded
    public LHBiblicaBreveJoinEntity lhBiblica;

    @Relation(
            parentColumn = "lecturaFK",
            entityColumn = "lecturaId",
            entity = LHBiblicaBreveEntity.class
    )
    public LHBiblicaBreveEntity biblica;

    @Relation(
            parentColumn = "responsorioFK",
            entityColumn = "responsorioId",
            entity = LHResponsorioBreveEntity.class
    )
    public LHResponsorioBreveEntity responsorio;

    public BiblicaBreve getDomainModel(Integer tiempoId) {
        BiblicaBreve dm=new BiblicaBreve();
        dm.setTexto(biblica.texto);
        dm.setRef(biblica.cita);
        dm.setResponsorio(responsorio.getDomainModel(tiempoId));
        return dm;
    }

}
