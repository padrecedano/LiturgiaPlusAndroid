package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.Patristica;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class HomilyAll {
    @Embedded
    public HomilyEntity homilia;

    @Relation(
            parentColumn = "opusFK",
            entityColumn = "opusID",
            entity = PaterOpusEntity.class
    )
    public PaterOpusAll paterOpusAll;

    public Patristica getPatristicaDomainModel() {
        Patristica theModel=new Patristica();
        //theModel.setTema(lhPatristica.getTema());
        theModel.setTexto(homilia.getTexto());
        theModel.setPadre(paterOpusAll.padre.getPadre());
        theModel.setObra(paterOpusAll.obra.getObra());
        theModel.setFuente(String.valueOf(homilia.getNumero()));
        return theModel;
    }

    public Homilia getDomainModel() {
        Homilia theModel=new Homilia();
        //theModel.setTema(lhPatristica.getTema());
        theModel.setHomilia(homilia.getTexto());
        theModel.setPadre(paterOpusAll.padre.getPadre());
        theModel.setObra(paterOpusAll.obra.getObra());
        //theModel.setTema(homilia.g);
        //theModel.setFuente(String.valueOf(homilia.getNumero()));
        return theModel;
    }

}
