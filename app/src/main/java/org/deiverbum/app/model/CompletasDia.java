package org.deiverbum.app.model;

public class CompletasDia {
    Salmodia salmodia;
    Oracion oracion;
    //BiblicaBreve biblicaBreve;
    private BiblicaBreve lecturaBreve;

    public Salmodia getSalmodia() {
        return this.salmodia;
    }

    public void setSalmodia(Salmodia salmodia) {
        this.salmodia = salmodia;
    }


    public BiblicaBreve getLecturaBreve() {
        return this.lecturaBreve;
    }

    public void setLecturaBreve(BiblicaBreve lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public Oracion getOracion() {
        return this.oracion;
    }

    public void setOracion(Oracion oracion) {
        this.oracion = oracion;
    }

}
