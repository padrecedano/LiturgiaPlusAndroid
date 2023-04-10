package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.NBSP_SALMOS;
import static org.deiverbum.app.utils.Constants.TITLE_PSALMODY;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

import java.util.Collections;
import java.util.List;

public class LHPsalmody {
    public Integer groupFK;
    public Integer readingFK;
    public Integer order;
    public Integer antiphonFK;
    public Integer themeFK;
    public Integer epigraphFK;
    public Integer part;
    @Ignore
    private int tipo;
    //@ColumnInfo(name = "salmos")
    @Ignore
    private List<LHPsalm> salmos;

    public LHPsalmody() {
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void sort() {
        Collections.sort(this.salmos);
    }

    public SpannableStringBuilder getSalmosForRead() {
        return getSalmosForRead(-1);
    }

    public SpannableStringBuilder getSalmos() {
        return getSalmos(-1);
    }

    public void setSalmos(List<LHPsalm> salmos) {
        this.salmos = salmos;
    }

    public SpannableStringBuilder getSalmos(int hourIndex) {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        String salmo;
        String preAntifona = "Ant. ";
        String antUnica;

        if (tipo == 1) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = salmos.get(hourIndex).getAntiphon();
            sb.append(antUnica);
        }
        if (tipo == 2) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = salmos.get(0).getAntiphon();
            sb.append(antUnica);
        }
        for (LHPsalm s : salmos) {
            SpannableStringBuilder tema = new SpannableStringBuilder("");
            SpannableStringBuilder parte = new SpannableStringBuilder("");
            SpannableStringBuilder intro = new SpannableStringBuilder("");
            SpannableStringBuilder ref = new SpannableStringBuilder("");
            String preRef = String.valueOf(s.getRef());

            if (tipo == 0) {
                sb.append(Utils.toRed(preAntifona + s.getTheOrder() + ". "));
                sb.append(Utils.fromHtml(s.getAntiphon()));
            }
            if (!s.getTheme().equals("")) {
                tema.append(Utils.toRed(s.getTheme()));
                tema.append(LS2);
            }

            if (!s.getEpigraph().equals("")) {
                intro.append(Utils.fromHtmlSmall(s.getEpigraph()));
                intro.append(LS2);
            }
            if (!s.getPart().equals("")) {
                parte.append(Utils.toRed(s.getPart()));
                parte.append(LS2);
            }
            if (!preRef.isEmpty()) {
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
                sb.append(getAntifonaLimpia(s.getAntiphon()));
                sb.append(LS2);
            }
        }
        if (tipo == 1) {
            sb.append(LS2);

            sb.append(Utils.toRed(preAntifona));
            antUnica = getAntifonaLimpia(salmos.get(hourIndex).getAntiphon());
            sb.append(antUnica);
            sb.append(LS2);
        }
        if (tipo == 2) {
            sb.append(LS2);
            sb.append(Utils.toRed(preAntifona));
            antUnica = getAntifonaLimpia(salmos.get(0).getAntiphon());
            sb.append(antUnica);
            sb.append(LS2);
        }
        return sb;
    }

    public SpannableStringBuilder getSalmosByIndex(int index) {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        String preAntifona = "Ant. ";

        sb.append(Utils.toRed(preAntifona));
        //sb.append(salmos.get(index).getAntiphon());
        LHPsalm s = salmos.get(index);
        sb.append(Utils.fromHtml(s.getAntiphon()));

        SpannableStringBuilder tema = new SpannableStringBuilder("");
        SpannableStringBuilder parte = new SpannableStringBuilder("");
        SpannableStringBuilder intro = new SpannableStringBuilder("");
        SpannableStringBuilder ref = new SpannableStringBuilder("");
        String preRef = String.valueOf(s.getRef());

        //sb.append(Utils.toRed(preAntifona + s.getTheOrder() + ". "));

        if (!s.getTheme().equals("")) {
            tema.append(Utils.toRed(s.getTheme()));
            tema.append(LS2);
        }

        if (!s.getEpigraph().equals("")) {
            intro.append(Utils.fromHtmlSmall(s.getEpigraph()));
            intro.append(LS2);
        }
        if (!s.getPart().equals("")) {
            parte.append(Utils.toRed(s.getPart()));
            parte.append(LS2);
        }
        if (!preRef.isEmpty()) {
            ref.append(Utils.LS);
            ref.append(s.getRef());
            ref.append(LS2);
        }

        sb.append(LS2);
        sb.append(ref);
        sb.append(tema);
        sb.append(intro);
        sb.append(parte);
        //salmo = Utils.getFormato(s.getSalmo());
        sb.append(Utils.fromHtml(Utils.getFormato(s.getSalmo())));

        if (s.getSalmo().endsWith("∸")) {
            sb.append(Utils.LS);
            sb.append(getNoGloria());
        } else {
            sb.append(LS2);
            sb.append(getFinSalmo());
        }

        sb.append(LS2);
        sb.append(Utils.toRed(preAntifona));
        sb.append(getAntifonaLimpia(s.getAntiphon()));
        sb.append(LS2);

        return sb;
    }

    public SpannableStringBuilder getSalmosForRead(int hourIndex) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        String salmo;
        String antUnica = "";

        if (tipo == 1) {
            antUnica = getAntifonaLimpia(salmos.get(hourIndex).getAntifonaForRead());
            sb.append(antUnica);
        }
        if (tipo == 2) {
            antUnica = getAntifonaLimpia(salmos.get(0).getAntifonaForRead());
            //antUnica = salmoCompleto.get(0).getAntifona();
            sb.append(antUnica);
        }
        for (LHPsalm s : salmos) {
            if (tipo == 0) {
                sb.append(Utils.fromHtml(s.getAntifonaForRead()));
            }

            sb.append(LS2);
            salmo = Utils.getFormatoForRead(s.getSalmo());
            sb.append(Utils.fromHtml(salmo));

            if (!(s.getSalmo().endsWith("∸"))) {
                sb.append(getFinSalmoForRead());
            }
            sb.append(LS2);

            if (tipo == 0) {
                sb.append(getAntifonaLimpia(s.getAntifonaForRead()));
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

    public SpannableStringBuilder getSalmosByIndexForRead(int index) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        String salmo;
        LHPsalm s = salmos.get(index);
        sb.append(Utils.fromHtml(s.getAntifonaForRead()));
        sb.append(LS2);
        salmo = Utils.getFormatoForRead(s.getSalmo());
        sb.append(Utils.fromHtml(salmo));
        if (!(s.getSalmo().endsWith("∸"))) {
            sb.append(getFinSalmoForRead());
        }
        sb.append(LS2);
        sb.append(getAntifonaLimpia(s.getAntifonaForRead()));
        sb.append(LS2);
        return sb;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle(TITLE_PSALMODY);
    }

    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_PSALMODY);
    }

    /**
     * <p>Obtiene el contenido de la salmodia formateado, para la vista.</p>
     *
     * @return Un {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getSalmos());
        return sb;
    }

    /**
     * <p>Obtiene el contenido de la salmodia formateado, para la vista.</p>
     *
     * @param hourIndex Un entero con el índice de la hora intermedia
     * @return Un {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */

    public SpannableStringBuilder getAll(int hourIndex) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getSalmos(hourIndex));
        return sb;
    }

    /**
     * <p>Obtiene el contenido de la salmodia formateado, para la vista.</p>
     *
     * @return Un {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    public String getAllForRead() {
        return getHeaderForRead() +
                getSalmosForRead();
    }

    /**
     * <p>Obtiene el contenido de la salmodia formateado, para la vista.</p>
     *
     * @param hourIndex Un entero con el índice de la hora intermedia
     * @return Un {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    public String getAllForRead(int hourIndex) {
        return getHeaderForRead() +
                getSalmosForRead(hourIndex);
    }

    /**
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
        return "Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                "Como era en el principio ahora y siempre, "
                + "por los siglos de los siglos. Amén.";
    }

    /**
     * @return La rúbrica cuando no se dice Gloria en los salmos.
     * @since 2022.01
     */
    public SpannableStringBuilder getNoGloria() {
        SpannableStringBuilder sb = new SpannableStringBuilder("No se dice Gloria");
        return Utils.toRedNew(sb);
    }

    /**
     * Método que limpia la segunda parte de la antífona, en el caso del símblo †
     *
     * @param sAntifona Una cadena con el texto de la antífona
     * @return La misma cadena, pero sin el referido símbolo
     */

    public String getAntifonaLimpia(String sAntifona) {
        return sAntifona.replace("†", "");
    }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */

    public void normalizeByTime(int calendarTime) {
        for (LHPsalm s : salmos) {
            s.setAntiphon(Utils.replaceByTime(s.getAntiphon(), calendarTime));
        }
    }

}