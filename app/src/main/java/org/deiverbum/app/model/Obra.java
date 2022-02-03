package org.deiverbum.app.model;

public class Obra {
    private String nombre;

    private Padre padre;

    public Obra() {
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Padre getPadre() {
        return this.padre;
    }

    public void setPadre(Padre padre) {
        this.padre = padre;
    }
}