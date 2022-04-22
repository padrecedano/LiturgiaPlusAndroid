package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import org.deiverbum.app.data.entity.LHResponsorioEntity;
import org.deiverbum.app.model.Responsorio;

/**
 * Transforma entidades  {@link org.deiverbum.app.data.entity.LHResponsorioEntity} (capa
 * de datos)
 * en objetos {@link org.deiverbum.app.model.Responsorio} (capa de dominio).
 */
public class LHResponsorioMapper {

    LHResponsorioMapper() {

    }


    /**
     * Método principal de transformación.
     *
     * @param theEntity Entidad a transformar.
     * @return {@link org.deiverbum.app.model.Responsorio} si hay un objeto
     * {@link org.deiverbum.app.data.entity.LHResponsorioEntity} válido.
     */
    public Responsorio transform(LHResponsorioEntity theEntity) {
        Responsorio theModel = new Responsorio();
        theModel.setTexto(theEntity.texto);
        theModel.setRef(theEntity.fuente);
        theModel.setForma(theEntity.tipo);

        //invitatorio.setAntifona(theEntity.antifona.getAntifona());
        //invitatorio.setId(theEntity.invitatorio.invitatorioId);
        return theModel;
    }


}
