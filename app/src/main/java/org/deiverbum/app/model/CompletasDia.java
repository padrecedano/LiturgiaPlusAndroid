package org.deiverbum.app.model;

public class CompletasDia {
    Salmodia salmodia;
    Oracion oracion;
    LecturaBreve lecturaBreve;

    public Salmodia getSalmodia() {
        return this.salmodia;
    }

    public void setSalmodia(Salmodia salmodia) {
        this.salmodia = salmodia;
    }


    public LecturaBreve getLecturaBreve() {
        return this.lecturaBreve;
    }

    public void setLecturaBreve(LecturaBreve lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public Oracion getOracion() {
        return this.oracion;
    }

    public void setOracion(Oracion oracion) {
        this.oracion = oracion;
    }

}
