package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.LecturaBreve;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "biblia_lectura",
        /*foreignKeys =
        {
            @ForeignKey(
                    entity = BibliaPericopaEntity.class,
                    parentColumns = "pericopaId",
                    childColumns = "pericopaFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        },*/
        indices={
                @Index(value={"libroId", "capitulo", "desde", "hasta"},unique = true)}
)
public class BibliaLecturaEntity {
    @NonNull
    public Integer getLibroId() {
        return libroId;
    }

    @NonNull
    public Integer getCapitulo() {
        return capitulo;
    }

    @NonNull
    public Integer getDesde() {
        return desde;
    }

    @NonNull
    public Integer getHasta() {
        return hasta;
    }

    @NonNull
    public String getCita() {
        return cita;
    }

    @NonNull
    public String getTexto() {
        return texto;
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "pericopaId")
    public Integer pericopaId;

    @NonNull
    @ColumnInfo(name = "libroId")
    public Integer libroId;

    @NonNull
    @ColumnInfo(name = "capitulo")
    public Integer capitulo;

    @NonNull
    @ColumnInfo(name = "desde")
    public Integer desde;

    @NonNull
    @ColumnInfo(name = "hasta")
    public Integer hasta;

    @NonNull
    @ColumnInfo(name = "cita")
    public String cita;

    @NonNull
    @ColumnInfo(name = "texto")
    public String texto;


/*
    public String getLibro() {
    }

 */
public Biblica getDomainModel(){
    Biblica theModel=new Biblica();
    //theModel.setLibro(String.valueOf(getLibroId()));
    theModel.setCapitulo(String.valueOf(getCapitulo()));
    theModel.setRef(String.valueOf(getHasta()));
    theModel.setVersoInicial(String.valueOf(getDesde()));
    theModel.setVersoFinal(String.valueOf(getHasta()));
    //theModel.setTema(lhBiblica.tema);
    theModel.setTexto(getTexto());
    //theModel.setResponsorio(lhResponsorioEntity.getDomainModel());
    return theModel;
}
    public LecturaBreve getDomainModelBreveSSS(){
        LecturaBreve theModel=new LecturaBreve();
        //theModel.setLibro();
        theModel.setCapitulo(String.valueOf(getCapitulo()));
        theModel.setRef(String.valueOf(getHasta()));
        theModel.setVersoInicial(String.valueOf(getDesde()));
        theModel.setVersoFinal(String.valueOf(getHasta()));
        //theModel.setTema(lhBiblica.tema);
        theModel.setTexto(getTexto());
        //theModel.setResponsorio(lhResponsorioEntity.getDomainModel());
        return theModel;
    }

}

