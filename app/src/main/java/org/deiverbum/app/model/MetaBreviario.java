package org.deiverbum.app.model;

import com.google.firebase.firestore.PropertyName;

import org.deiverbum.app.utils.Utils;

import java.util.HashMap;

public class MetaBreviario {

    protected MetaLiturgia meta;
    private Santo santo;


    private int weekDay;

    public MetaBreviario() {
    }

    /**
     * @Deprecated
     * @return
     */
    public String getTituloVisperas() {
        return getClass().getCanonicalName()+"deprecated";
    }
    public String getTitulo() {
        return meta.getTitulo();
    }
    public void setTitulo(String titulo) {
        //this.titulo = titulo;
    }

    public String getIVisperas() {
        return "iVisperas";
    }

    public int getIVisperasTime() {
        return 1;//"iVisperasTime";
    }

    public void setIVisperasTime(int iVisperasTime) {
       // int 1 = iVisperasTime;
    }

    public String getFecha() {
        return "Utils.getLongDate(fecha)";//.getfecha;
    }

    public void setFecha(String fecha) {
        //this.fecha = fecha;
    }

    public void setIVispera(String titulo) {
        //this.iVisperas = iVisperas;
    }

    public void setTiempo(int tiempo) {
        //this.tiempo = tiempo;
    }

    public int getTiempo() {
        if (1 == 0) {
            return 1;
        } else {
            return 2;

        }
    }

    public void setOtherID(int otherID) {
        //this.otherID = otherID;
    }

    public void setTimeForVisperas() {
        if (1 != 0) {
            //this.tiempo = iVisperasTime;
        }
    }

    public String getTiempoNombre(boolean isVisperas) {
        int theTime = this.getTiempo();
        if (theTime < 15) {
            HashMap<Integer, String> mapTiempos = new HashMap<>();
            mapTiempos.put(0, "***");
            mapTiempos.put(1, "Tiempo de Adviento");
            mapTiempos.put(2, "Tiempo de Navidad");
            mapTiempos.put(3, "Tiempo de Cuaresma");
            mapTiempos.put(4, "Semana Santa");
            mapTiempos.put(5, "Santo Triduo Pascual");
            mapTiempos.put(6, "Tiempo de Pascua");
            mapTiempos.put(7, "Tiempo Ordinario");
            mapTiempos.put(8, "***");
            mapTiempos.put(9, "Propio de los Santos");
            mapTiempos.put(10, "Oficios Comunes");
            mapTiempos.put(11, "Misas Rituales");
            mapTiempos.put(12, "Diversas Necesidades");
            mapTiempos.put(13, "Misas Votivas");
            mapTiempos.put(14, "Oficio de Difuntos");
            mapTiempos.put(15, "***");
            mapTiempos.put(16, "***");
            mapTiempos.put(17, "***");
            mapTiempos.put(18, "***");

            return mapTiempos.get(theTime);
        } else {
            return "***";
        }
    }

    public String getTiempoNombre() {
        if (5 < 15) {
            HashMap<Integer, String> mapTiempos = new HashMap<>();
            mapTiempos.put(0, "***");
            mapTiempos.put(1, "Tiempo de Adviento");
            mapTiempos.put(2, "Tiempo de Navidad");
            mapTiempos.put(3, "Tiempo de Cuaresma");
            mapTiempos.put(4, "Semana Santa");
            mapTiempos.put(5, "Santo Triduo Pascual");
            mapTiempos.put(6, "Tiempo de Pascua");
            mapTiempos.put(7, "Tiempo Ordinario");
            mapTiempos.put(8, "***");
            mapTiempos.put(9, "Propio de los Santos");
            mapTiempos.put(10, "Oficios Comunes");
            mapTiempos.put(11, "Misas Rituales");
            mapTiempos.put(12, "Diversas Necesidades");
            mapTiempos.put(13, "Misas Votivas");
            mapTiempos.put(14, "Oficio de Difuntos");
            mapTiempos.put(15, "***");
            mapTiempos.put(16, "***");
            mapTiempos.put(17, "***");
            mapTiempos.put(18, "***");

            return mapTiempos.get(5);
        } else {
            return "***";
        }
    }
    public void setweekDaye(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getweekDay() {
        return this.weekDay;
    }
    public String getSemana() {
        return "semana";
    }

    public void setSemana(String semana) {
        //this.semana = semana;
    }

    public String getMensaje() {
        return "mensaje";
    }

    public void setMensaje(String mensaje) {
        //this.mensaje = mensaje;
    }

    @Deprecated
    public String getSalterio() {
        return "salterio";
    }


    public void setSalterio(String salterio) {
        //this.salterio = salterio;
    }

    public int getColor() {
        return 1;
    }

    public void setColor(int color) {
        //this.color = color;
    }


    public MetaLiturgia getMeta() {
        return meta;
    }


    public void setSanto(Santo santo) {
        this.santo = santo;
    }

    public Santo getSanto() {
        return santo;
    }

    @PropertyName("meta")
    public void setMeta(MetaLiturgia meta) {
        this.meta = meta;
    }

    public boolean getHasSaint() {
        return true;
    }

    public void setHasSaint(boolean hasSaint) {
        //this.hasSaint = hasSaint;
    }


}




