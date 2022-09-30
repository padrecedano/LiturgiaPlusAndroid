package org.deiverbum.app.model;

import org.deiverbum.app.utils.Utils;

public class IntroViaCrucis {
    String saludo;
    String intro;
    String oracion;

    @SuppressWarnings("unused")
    public String getSaludo() {
        return this.saludo;
    }

    @SuppressWarnings("unused")
    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }

    public String getIntro() {
        return Utils.getFormato(intro);
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getOracion() {
        return Utils.getFormato(oracion);
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

}
