package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import org.deiverbum.app.data.entity.BiblicaOficioWithResponsorio;
import org.deiverbum.app.model.Biblica;

/**
 * Transforma entidades  {@link org.deiverbum.app.data.entity.BibliaLecturaEntity} (capa de datos)
 * en objetos {@link org.deiverbum.app.model.Biblica} (capa de dominio).
 */
public class BiblicaDataMapper {

    BiblicaDataMapper() {

    }


    /**
     * Método principal de transformación.
     *
     * @param theEntity Entidad a transformar.
     * @return {@link org.deiverbum.app.model.Biblica} si hay un objeto
     * {@link BiblicaOficioWithResponsorio} válido.
     */
    public Biblica transform(BiblicaOficioWithResponsorio theEntity) {
        Biblica theModel = new Biblica();
        //theModel.setTema(theEntity.biblicaOficioWithResponsorio.tema);
        //theModel.setTexto(theEntity.bibliaLectura.texto);
        //LHResponsorioMapper lhResponsorioMapper=new LHResponsorioMapper();
        //theModel.setResponsorio(lhResponsorioMapper.transform(theEntity
        // .lhResponsorioEntity));
        //invitatorio.setAntifona(theEntity.antifona.getAntifona());
        //invitatorio.setId(theEntity.invitatorio.invitatorioId);
        return theModel;
    }


}
