package org.deiverbum.app.model;

public class PaterOpus {
    private String nombre;

    private Pater padre;

    public PaterOpus() {
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pater getPadre() {
        return this.padre;
    }

    public void setPadre(Pater padre) {
        this.padre = padre;
    }
}