package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.MassReading;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "bible_reading",
        foreignKeys =
        {
            @ForeignKey(
                    entity = BiblieBookEntity.class,
                    parentColumns = "bookID",
                    childColumns = "bookFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        },
        indices={
                @Index(value={"bookFK", "verseChapter", "verseFrom", "verseTo"},unique = true)}
)
public class BibleReadingEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "readingID")
    public Integer lecturaId=0;

    @NonNull
    @ColumnInfo(name = "bookFK")
    public Integer libroFK=0;

    @NonNull
    @ColumnInfo(name = "verseChapter")
    public Integer capitulo=0;

    @NonNull
    @ColumnInfo(name = "verseFrom")
    @SerializedName("versoInicial")
    public Integer desde=0;

    @NonNull
    @ColumnInfo(name = "verseTo")
    public Integer hasta=0;

    @NonNull
    @ColumnInfo(name = "quote")
    public String cita="";

    @NonNull
    @ColumnInfo(name = "text")
    public String texto="";


    @SuppressWarnings("unused")
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


    public Biblical getDomainModel(){
    Biblical theModel=new Biblical();
    //theModel.setLibro(String.valueOf(getLibroId()));
    theModel.setCapitulo(String.valueOf(getCapitulo()));
    theModel.setCita(String.valueOf(getHasta()));
    theModel.setVersoInicial(String.valueOf(getDesde()));
    theModel.setVersoFinal(String.valueOf(getHasta()));
    //theModel.setTema(biblicaOficioWithResponsorio.tema);
    theModel.setTexto(getTexto());
    //theModel.setResponsorio(lhResponsorioEntity.getDomainModel());
    return theModel;
}

    public MassReading getDomainModelMisa(){
        MassReading theModel=new MassReading();
        //theModel.setLibro(String.valueOf(getLibroId()));
        //theModel.setLibro()
        theModel.setCapitulo(String.valueOf(getCapitulo()));
        theModel.setCita(String.valueOf(getHasta()));
        theModel.setVersoInicial(String.valueOf(getDesde()));
        theModel.setVersoFinal(String.valueOf(getHasta()));
        //theModel.setTema(biblicaOficioWithResponsorio.tema);
        theModel.setTexto(getTexto());
        //theModel.setResponsorio(lhResponsorioEntity.getDomainModel());
        return theModel;
    }

}

