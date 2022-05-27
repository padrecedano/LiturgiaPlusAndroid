package org.deiverbum.app.data.entity.mapper;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

import org.deiverbum.app.data.entity.LHPatristica;
import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Patristica;

/**
 * Transforma entidades  {@link org.deiverbum.app.data.entity.LHPatristicaEntity} (capa de datos)
 * en objetos {@link org.deiverbum.app.model.Patristica} (capa de dominio).
 */
public class PatristicaDataMapper {

    PatristicaDataMapper() {

    }


    /**
     * Método principal de transformación.
     *
     * @param theEntity Entidad a transformar.
     * @return {@link org.deiverbum.app.model.Patristica} si hay un objeto
     * {@link org.deiverbum.app.data.entity.LHPatristica} válido.
     */
    public Patristica transform(LHPatristica theEntity) {
        Patristica theModel = new Patristica();
        theModel.setTema(theEntity.lhPatristica.tema);
        theModel.setTexto(theEntity.homiliaAll.homilia.texto);
        theModel.setPadre(theEntity.homiliaAll.obraWithPadre.padre.padre);
        theModel.setObra(theEntity.homiliaAll.obraWithPadre.obra.obra);
        theModel.setFuente(String.valueOf(theEntity.homiliaAll.homilia.numero));
        LHResponsorioMapper lhResponsorioMapper=new LHResponsorioMapper();
        //theModel.setResponsorio(lhResponsorioMapper.transform(theEntity.lhResponsorio));
        //invitatorio.setAntifona(theEntity.antifona.getAntifona());
        //invitatorio.setId(theEntity.invitatorio.invitatorioId);
        return theModel;
    }


}
