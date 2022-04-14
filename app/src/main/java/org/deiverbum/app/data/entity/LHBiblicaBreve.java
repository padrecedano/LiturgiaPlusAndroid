package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.LecturaBreve;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgia de las Horas,
 *  desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHBiblicaBreve {
    @Embedded
    public LHBiblicaBreveEntity lhBiblica;

    @Relation(
            parentColumn = "pericopaFK",
            entityColumn = "pericopaId",
            entity = BibliaLecturaEntity.class/*,
            associateBy = @Junction(MemberTeamMap.class)*/
    )
    public BibliaLecturaEntity bibliaLectura;

    @Relation(
            parentColumn = "responsorioFK",
            entityColumn = "responsorioId",
            entity = LHResponsorioEntity.class
    )
    public LHResponsorioEntity lhResponsorioEntity;



    public LecturaBreve getDomainModelBreve(Integer timeId){
        LecturaBreve theModel= bibliaLectura.getDomainModelBreve();
        /*theModel.setLibro(String.valueOf(bibliaLectura.libroId));
        theModel.setCapitulo(String.valueOf(bibliaLectura.capitulo));
        theModel.setRef(String.valueOf(bibliaLectura.capitulo));
        theModel.setVersoInicial(String.valueOf(bibliaLectura.desde));
        theModel.setVersoFinal(String.valueOf(bibliaLectura.hasta));*/
        //theModel.setTema(lhBiblica.tema);
        //theModel.setTexto(bibliaLectura.texto);
        theModel.setResponsorio(lhResponsorioEntity.getDomainModel(timeId));
        return theModel;
    }

}
