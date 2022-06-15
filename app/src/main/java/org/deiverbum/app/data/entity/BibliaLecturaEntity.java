package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaBreve;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "biblia_lectura",
        foreignKeys =
        {
            @ForeignKey(
                    entity = BibliaLibroEntity.class,
                    parentColumns = "libroId",
                    childColumns = "libroFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        },
        indices={
                @Index(value={"libroFK", "capitulo", "desde", "hasta"},unique = true)}
)
public class BibliaLecturaEntity {
    @NonNull
    public Integer getLibroFK() {
        return libroFK;
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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lecturaId")
    public Integer lecturaId;

    @NonNull
    @ColumnInfo(name = "libroFK")
    public Integer libroFK;

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
    //theModel.setTema(biblicaOficioWithResponsorio.tema);
    theModel.setTexto(getTexto());
    //theModel.setResponsorio(lhResponsorioEntity.getDomainModel());
    return theModel;
}
    public BiblicaBreve getDomainModelBreveSSS(){
        BiblicaBreve theModel=new BiblicaBreve();
        //theModel.setLibro();
        theModel.setCapitulo(String.valueOf(getCapitulo()));
        theModel.setRef(String.valueOf(getHasta()));
        theModel.setVersoInicial(String.valueOf(getDesde()));
        theModel.setVersoFinal(String.valueOf(getHasta()));
        //theModel.setTema(biblicaOficioWithResponsorio.tema);
        theModel.setTexto(getTexto());
        //theModel.setResponsorio(lhResponsorioEntity.getDomainModel());
        return theModel;
    }

}

