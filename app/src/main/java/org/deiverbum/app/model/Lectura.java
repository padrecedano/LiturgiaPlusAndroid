package org.deiverbum.app.model;

class Lectura {
    private int orden;
    private String libro;
    private String abreviatura;
    private String ref;
    private String tema;
    private String texto;

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    @SuppressWarnings("unused")
    public String getAbreviatura() {
        return abreviatura;
    }

    @SuppressWarnings("unused")
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
