package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Lecturas;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.Salmodia;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayMisaLecturas {

    @Embedded
    public Today today;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "feriaFK",
            entityColumn = "liturgiaId"
    )
    public LiturgiaWithTiempo feria;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "previoId",
            entityColumn = "liturgiaId"
    )
    public LiturgiaWithTiempo previo;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId"
    )
    public SantoWithAll santo;
/*
    @Relation(
            entity = MisaLecturaEntity.class,
            parentColumn = "mLecturasFK",
            entityColumn = "liturgiaFK"
    )
    public List<MisaWithLecturas> lecturas;
*/
    @Relation(
            entity = LiturgyGroupEntity.class,
            parentColumn = "mLecturasFK",
            entityColumn = "groupID"
    )
    public MisaWithLecturasBis lecturass;

    public Hoy getToday(){
        Hoy dm = new Hoy();
        //dm.setFeria(feria.getDomainModel());
        dm.setFeria(lecturass.getLiturgia());

        dm.setFecha(String.valueOf(today.getHoy()));
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        dm.setMLecturasFK(today.mLecturasFK);
        dm.setTitulo(feria.getDomainModel().getNombre());
        return dm;
    }

//TODO Ordenar las lecturas con comparator (cf. Salmodia)
    public MisaLecturas getDomainModel(){
        MisaLecturas dm=new MisaLecturas();
        dm.setHoy(getToday());
        List<BiblicaMisa> listModel = new ArrayList<>();
        for (MassReadingWithAll item : lecturass.lectura) {
                listModel.add(item.getDomainModel());
        }
        dm.setLecturas(listModel);
        dm.sort();
        return dm;
    }



}
