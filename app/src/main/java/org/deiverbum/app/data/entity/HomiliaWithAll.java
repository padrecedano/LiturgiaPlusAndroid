package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Homilia;
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
            parentColumn = "opusFK",
            entityColumn = "opusID",
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

    public Homilia getDomainModel() {
        Homilia theModel=new Homilia();
        //theModel.setTema(lhPatristica.getTema());
        theModel.setHomilia(homilia.getTexto());
        theModel.setPadre(obraWithPadre.padre.getPadre());
        theModel.setObra(obraWithPadre.obra.getObra());
        //theModel.setTema(homilia.g);
        //theModel.setFuente(String.valueOf(homilia.getNumero()));
        return theModel;
    }

}
