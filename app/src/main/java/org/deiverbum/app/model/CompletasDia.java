package org.deiverbum.app.model;

public class CompletasDia {
    Salmodia salmodia;
    Oracion oracion;
    BiblicaBreve biblicaBreve;

    public Salmodia getSalmodia() {
        return this.salmodia;
    }

    public void setSalmodia(Salmodia salmodia) {
        this.salmodia = salmodia;
    }


    public BiblicaBreve getLecturaBreve() {
        return this.biblicaBreve;
    }

    public void setLecturaBreve(BiblicaBreve biblicaBreve) {
        this.biblicaBreve = biblicaBreve;
    }

    public Oracion getOracion() {
        return this.oracion;
    }

    public void setOracion(Oracion oracion) {
        this.oracion = oracion;
    }

}
