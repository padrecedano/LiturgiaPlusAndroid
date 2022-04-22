package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import org.deiverbum.app.data.entity.LHOracion;
import org.deiverbum.app.model.Oracion;

/**
 * Transforma entidades  {@link org.deiverbum.app.data.entity.LHOracion} (capa de datos)
 * en objetos {@link Oracion} (capa de dominio).
 */
public class LHOracionDataMapper {

    LHOracionDataMapper() {

    }


    /**
     * Método principal de transformación.
     *
     * @param theEntity Entidad a transformar.
     * @return {@link Oracion} si hay un objeto
     * {@link LHOracion} válido.
     */
    public Oracion transform(LHOracion theEntity) {
        Oracion theModel = new Oracion();
        theModel.setTexto(theEntity.oracionEntity.texto);
        return theModel;
    }


}
