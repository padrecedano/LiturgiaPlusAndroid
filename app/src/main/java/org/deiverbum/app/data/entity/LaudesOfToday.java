package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Patristica;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LaudesOfToday {

    @Embedded
    public Today today;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId" //liturgiaId
    )
    public SantoEntity santo;

    @Relation(
            entity = InvitatorioEntity.class,
            parentColumn = "invitatorioFK",
            entityColumn = "invitatorioId"
    )
    public InvitatorioWithAntifona invitatorio;

    @Relation(
            entity = HimnoEntity.class,
            parentColumn = "lHimnoFK",
            entityColumn = "himnoId"
    )
    public HimnoEntity himno;

    @Relation(
            entity = LHBiblicaEntity.class,
            parentColumn = "lBiblicaFK",
            entityColumn = "biblicaId"
    )
    public LHBiblica biblica;


    @Relation(
            entity = SalmodiaEntity.class,
            parentColumn = "lSalmodiaFK",
            entityColumn = "liturgiaId"
    )
    public List<SalmodiaWithSalmos> salmos;

    @Relation(
            entity = LHOracionEntity.class,
            parentColumn = "lOracionFK",
            entityColumn = "liturgiaId"
    )
    public LHOracion lhOracion;



    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "feriaFK",
            entityColumn = "liturgiaId"
    )
    public LiturgiaEntity feria;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "previoId",
            entityColumn = "liturgiaId"
    )
    public LiturgiaEntity previo;

    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        theModel.setLiturgiaFeria(feria.getDomainModel());
        if(today.previoId!=null){
            //theModel.setLiturgiaPrevio(previo.getDomainModel());
        }
        theModel.setFecha(String.valueOf(today.hoy));
        theModel.setColor(feria.colorFK);
        theModel.setIdHour(6);
        theModel.setCalendarTime(feria.colorFK);
        theModel.setHasSaint(false);
        theModel.setIdBreviario(feria.colorFK);
        theModel.setIdDia(feria.colorFK);
        theModel.setIdLecturas(today.mLecturasFK);
        theModel.setIdPrevio(1);
        theModel.setIdSemana(1);
        theModel.setIdTiempo(1);
        theModel.setIdTiempoPrevio(1);
        theModel.setTitulo(feria.nombre);
        return theModel;
    }
    public Himno getHimno(){
        Himno modelHimno = new Himno();
        modelHimno.setTexto(himno.getHimno());
        return modelHimno;
    }


    public Biblica getBiblica(){
        return biblica.getDomainModel();
    }


}
