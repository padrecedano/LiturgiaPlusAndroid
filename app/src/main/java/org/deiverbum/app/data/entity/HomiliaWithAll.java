package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Responsorio;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class HomiliaWithAll {
    @Embedded
    public HomiliaEntity homilia;

    @Relation(
            parentColumn = "obraFK",
            entityColumn = "obraId",
            entity = ObraEntity.class
    )
    public ObraWithPadre obraWithPadre;

    public Patristica getPatristicaDomainModel() {
        Patristica theModel=new Patristica();
        //theModel.setTema(lhPatristica.getTema());
        theModel.setTexto(homilia.getTexto());
        theModel.setPadre(obraWithPadre.padre.getPadre());
        theModel.setObra(obraWithPadre.obra.getObra());
        theModel.setFuente(String.valueOf(homilia.getNumero()));
        return theModel;
    }

}
