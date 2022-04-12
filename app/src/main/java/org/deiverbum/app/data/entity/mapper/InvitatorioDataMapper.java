package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.entity.InvitatorioWithAntifona;
import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Salmo;
import org.deiverbum.app.model.Salmodia;

import java.util.ArrayList;
import java.util.List;

/**
 * Transforma entidades  {@link InvitatorioWithAntifona} (capa de datos)
 * en objetos {@link Invitatorio} (capa de dominio).
 */
public class InvitatorioDataMapper {

    InvitatorioDataMapper() {

    }


    /**
     * Método principal de transformación.
     *
     * @param theEntity Entidad a transformar.
     * @return {@link Invitatorio} si hay un objeto
     * {@link InvitatorioWithAntifona} válido.
     */
    public Invitatorio transform(InvitatorioWithAntifona theEntity) {
        Invitatorio invitatorio = new Invitatorio();
        invitatorio.setAntifona(theEntity.antifona.getAntifona());
        invitatorio.setId(theEntity.invitatorio.invitatorioId);
        return invitatorio;
    }


}
