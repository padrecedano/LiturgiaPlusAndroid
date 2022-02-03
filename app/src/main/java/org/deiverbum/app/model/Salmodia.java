package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.NBSP_SALMOS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Salmodia {
    private int tipo;
    private List<Salmo> salmos;

    public Salmodia() {
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public SpannableStringBuilder getAllForReads() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (Salmo s : salmos) {
            String ant = "<p>" + s.getAntifona() + ".</p>";
            ssb.append(ant);
            ssb.append(LS2);
            ssb.append(s.getSalmo());
            ssb.append(LS2);
            if (s.getSalmo().endsWith("∸")) {
            } else {
                ssb.append(getFinSalmo());
            }
            ssb.append(ant);
            //ssb.append(SEPARADOR);
        }
        return ssb;
    }

    public SpannableStringBuilder getSalmosForRead() {
        return getSalmosForRead(-1);
    }

    public SpannableStringBuilder getSalmos() {
        return getSalmos(-1);
    }

    public void setSalmos(List<Salmo> salmos) {
        this.salmos = salmos;
    }

    public SpannableStringBuilder getSalmoCompletas(int timeID) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        String salmo = "";
        String preAntifona = "Ant. ";
        String antUnica = "";

        if (timeID == 6) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = "Aleluya, aleluya, aleluya.";
            sb.append(antUnica);
        }

        for (Salmo s : salmos) {
            SpannableStringBuilder tema = new SpannableStringBuilder();
            SpannableStringBuilder parte = new SpannableStringBuilder();
            SpannableStringBuilder intro = new SpannableStringBuilder();
            SpannableStringBuilder ref = new SpannableStringBuilder();
            String preRef = String.valueOf(s.getRef());

            if (timeID != 6) {
                sb.append(Utils.toRed(preAntifona + s.getOrden() + ". "));
                sb.append(Utils.fromHtml(s.getAntifona()));
            }
            if (!s.getTema().equals("")) {
                tema.append(Utils.toRed(s.getTema()));
                tema.append(LS2);
            }

            if (!s.getIntro().equals("")) {
                intro.append(Utils.fromHtmlSmall(s.getIntro()));
                intro.append(LS2);
            }
            if (!s.getParte().equals("")) {
                parte.append(Utils.toRed(s.getParte()));
                parte.append(LS2);
            }
            if (preRef != null && !preRef.isEmpty()) {
                ref.append(Utils.LS);
                ref.append(s.getRef());
                ref.append(LS2);
            }

            sb.append(LS2);
            sb.append(ref);
            sb.append(tema);
            sb.append(intro);
            sb.append(parte);
            salmo = Utils.getFormato(s.getSalmo());
            sb.append(Utils.fromHtml(salmo));

            if (s.getSalmo().endsWith("∸")) {
                sb.append(Utils.LS);
                sb.append(Utils.getNoGloria());
            } else {
                sb.append(LS2);
                sb.append(getFinSalmo());
            }


            if (timeID != 6) {
                sb.append(LS2);
                sb.append(Utils.toRed(preAntifona));
                sb.append(getAntifonaLimpia(s.getAntifona()));
                sb.append(LS2);
            }
        }
        if (timeID == 6) {
            sb.append(LS2);
            sb.append(Utils.toRed(preAntifona));
            sb.append(antUnica);
            sb.append(LS2);
        }

        return sb;
    }


    public SpannableStringBuilder getSalmoCompletasForRead(int timeID) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        String salmo = "";
        String antUnica = "";

        if (timeID == 6) {
            antUnica = "Aleluya, aleluya, aleluya.";
            sb.append(antUnica);
        }

        for (Salmo s: salmos) {
            SpannableStringBuilder tema = new SpannableStringBuilder();
            SpannableStringBuilder parte = new SpannableStringBuilder();
            SpannableStringBuilder intro = new SpannableStringBuilder();
            SpannableStringBuilder ref = new SpannableStringBuilder();
            String preRef = String.valueOf(s.getRef());

            if (timeID != 6) {
                sb.append(Utils.fromHtml(s.getAntifona()));
            }


            sb.append(LS2);
            sb.append(ref);
            sb.append(tema);
            sb.append(intro);
            sb.append(parte);
            //salmo = Utils.getFormato(s.getSalmo());
            sb.append(Utils.fromHtml(s.getSalmo()));

            if (s.getSalmo().endsWith("∸")) {
                sb.append(Utils.LS);
                sb.append(Utils.getNoGloria());
            } else {
                sb.append(LS2);
                sb.append(getFinSalmo());
            }


            if (timeID != 6) {
                sb.append(LS2);
                sb.append(getAntifonaLimpia(s.getAntifona()));
                sb.append(LS2);
            }
        }
        if (timeID == 6) {
            sb.append(antUnica);
            sb.append(LS2);
        }

        return sb;
    }

    public SpannableStringBuilder getSalmos(int hourIndex) {
        //String tipo = this.tipo;
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        String salmo = "";
        String preAntifona = "Ant. ";
        String antUnica = "";

        if (tipo == 1) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = salmos.get(hourIndex).getAntifona();
            sb.append(antUnica);
        }
        if (tipo == 2) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = salmos.get(0).getAntifona();
            sb.append(antUnica);
        }
        for (Salmo s : salmos) {
            SpannableStringBuilder tema = new SpannableStringBuilder("");
            SpannableStringBuilder parte = new SpannableStringBuilder("");
            SpannableStringBuilder intro = new SpannableStringBuilder("");
            SpannableStringBuilder ref = new SpannableStringBuilder("");
            String preRef = String.valueOf(s.getRef());

            if (tipo == 0) {
                sb.append(Utils.toRed(preAntifona + s.getOrden() + ". "));
                sb.append(Utils.fromHtml(s.getAntifona()));
            }
            if (!s.getTema().equals("")) {
                tema.append(Utils.toRed(s.getTema()));
                tema.append(LS2);
            }

            if (!s.getIntro().equals("")) {
                intro.append(Utils.fromHtmlSmall(s.getIntro()));
                intro.append(LS2);
            }
            if (!s.getParte().equals("")) {
                parte.append(Utils.toRed(s.getParte()));
                parte.append(LS2);
            }
            if (preRef != null && !preRef.isEmpty()) {
                ref.append(Utils.LS);
                ref.append(s.getRef());
                ref.append(LS2);
            }

            sb.append(LS2);
            sb.append(ref);
            sb.append(tema);
            sb.append(intro);
            sb.append(parte);
            salmo = Utils.getFormato(s.getSalmo());
            sb.append(Utils.fromHtml(salmo));

            if (s.getSalmo().endsWith("∸")) {
                sb.append(Utils.LS);
                sb.append(getNoGloria());
            } else {
                sb.append(LS2);
                sb.append(getFinSalmo());
            }

            if (tipo == 0) {
                sb.append(LS2);
                sb.append(Utils.toRed(preAntifona));
                sb.append(getAntifonaLimpia(s.getAntifona()));
                sb.append(LS2);
            }
        }
        if (tipo == 1) {
            sb.append(LS2);

            sb.append(Utils.toRed(preAntifona));
            antUnica = getAntifonaLimpia(salmos.get(hourIndex).getAntifona());
            sb.append(antUnica);
            sb.append(LS2);
        }
        if (tipo == 2) {
            sb.append(LS2);
            sb.append(Utils.toRed(preAntifona));
            antUnica = getAntifonaLimpia(salmos.get(0).getAntifona());
            sb.append(antUnica);
            sb.append(LS2);
        }
        return sb;
    }

    public SpannableStringBuilder getSalmosForRead(int hourIndex) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        String salmo = "";
        String antUnica = "";

        if (tipo == 1) {
            antUnica = getAntifonaLimpia(salmos.get(hourIndex).getAntifona());
            sb.append(antUnica);
        }
        if (tipo == 2) {
            antUnica = getAntifonaLimpia(salmos.get(0).getAntifona());
            //antUnica = salmoCompleto.get(0).getAntifona();
            sb.append(antUnica);
        }
        for (Salmo s : salmos) {
            if (tipo == 0) {
                sb.append(Utils.fromHtml(s.getAntifona()));
            }

            sb.append(LS2);
            salmo = Utils.getFormato(s.getSalmo());
            sb.append(Utils.fromHtml(salmo));
            //sb.append(SEPARADOR);

            if (!(s.getSalmo().endsWith("∸"))) {
                sb.append(getFinSalmo());
            }
            sb.append(LS2);

            if (tipo == 0) {
                sb.append(getAntifonaLimpia(s.getAntifona()));
                sb.append(LS2);
            }
        }
        if (tipo == 1) {
            sb.append(getAntifonaLimpia(antUnica));
            sb.append(LS2);
        }
        if (tipo == 2) {
            sb.append(getAntifonaLimpia(antUnica));
            sb.append(LS2);
        }

        return sb;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("SALMODIA");
    }

    public String getHeaderForRead() {
        return "SALMODIA.";
    }


    /**
     * <p>Obtiene el contenido de la salmodia formateado, para la vista.</p>
     * @since 2022.01
     * @return Un {@link SpannableStringBuilder con el contenido.}
     */
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getSalmos());
        return sb;
    }

    /**
     *
     * <p>Obtiene el contenido de la salmodia formateado, para la vista.</p>
     * @since 2022.01
     * @param hourIndex Un entero con el índice de la hora intermedia
     * @return Un {@link SpannableStringBuilder con el contenido.}
     * @return
     */

    public SpannableStringBuilder getAll(int hourIndex) {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getSalmos(hourIndex));
        return sb;
    }

    /**
     * <p>Obtiene el contenido de la salmodia formateado, para la vista.</p>
     * @since 2022.01
     * @return Un {@link SpannableStringBuilder con el contenido.}
     */
    public String getAllForRead() {
        StringBuilder sb=new StringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getSalmosForRead());
        return sb.toString();
    }

    /**
     * <p>Obtiene el contenido de la salmodia formateado, para la vista.</p>
     * @since 2022.01
     * @param hourIndex Un entero con el índice de la hora intermedia
     * @return Un {@link SpannableStringBuilder con el contenido.}
     */
    public String getAllForRead(int hourIndex) {
        StringBuilder sb=new StringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getSalmosForRead(hourIndex));
        return sb.toString();
    }
    /**
     *
     * @return Texto al final de cada salmo
     * @since 2022.01
     */
    public Spanned getFinSalmo() {
        String fin = "Gloria al Padre, y al Hijo, y al Espíritu Santo." + BR
                + NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                + NBSP_SALMOS + "por los siglos de los siglos. Amén.";
        return Utils.fromHtml(fin);
    }

    public String getFinSalmoForRead() {
        String fin = "Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                "Como era en el principio ahora y siempre, "
                + "por los siglos de los siglos. Amén.";
        return fin;
    }

    /**
     *
     * @return La rúbrica cuando no se dice Gloria en los salmos.
     * @sice 2022.01
     */
    public SpannableStringBuilder getNoGloria() {
        SpannableStringBuilder sb = new SpannableStringBuilder("No se dice Gloria");
        return Utils.toRedNew(sb);
    }

    /**
     * Método que limpia la segunda parte de la antífona, en el caso del símblo †
     * @param sAntifona Una cadena con el texto de la antífona
     * @return La misma cadena, pero sin el referido símbolo
     */

    public String getAntifonaLimpia(String sAntifona) {
        return sAntifona.replace("†", "");
    }


    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */

    public void normalizeByTime(int calendarTime) {
        for (Salmo s : salmos) {
            s.setAntifona(Utils.replaceByTime(s.getAntifona(),calendarTime));
        }


    }



    }