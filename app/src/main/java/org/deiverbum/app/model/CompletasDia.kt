package org.deiverbum.app.model;

public class CompletasDia {
    LHPsalmody salmodia;
    Prayer oracion;
    //BiblicalShort biblicaBreve;
    private BiblicalShort lecturaBreve;

    public LHPsalmody getSalmodia() {
        return this.salmodia;
    }

    public void setSalmodia(LHPsalmody salmodia) {
        this.salmodia = salmodia;
    }


    public BiblicalShort getLecturaBreve() {
        return this.lecturaBreve;
    }

    public void setLecturaBreve(BiblicalShort lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public Prayer getOracion() {
        return this.oracion;
    }

    public void setOracion(Prayer oracion) {
        this.oracion = oracion;
    }

}
