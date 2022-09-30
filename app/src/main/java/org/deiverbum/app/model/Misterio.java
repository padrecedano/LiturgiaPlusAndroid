package org.deiverbum.app.model;

import java.util.Arrays;
import java.util.List;

public class Misterio {
    public int id;
    public String titulo;
    public List<String> contenido;
    public final List<String> ordinalName= Arrays.asList(
            "Primer Misterio",
            "Segundo Misterio",
            "Tercer Misterio",
            "Cuarto Misterio",
            "Quinto Misterio");

    public Misterio(){
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @SuppressWarnings("unused")
    public List<String> getContenidoAll(int dayCode) {
        return contenido;
    }

    public List<String> getContenido() {
        return contenido;
    }

    public void setContenido(List<String> contenido) {
        this.contenido = contenido;
    }
}