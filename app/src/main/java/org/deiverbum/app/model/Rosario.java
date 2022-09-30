package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.util.List;
import java.util.Locale;

@SuppressWarnings("SameReturnValue")
public class Rosario {
    public String saludo;
    public String padrenuestro;
    public String avemaria;
    public String gloria;
    public String letanias;
    public String oracion;
    public String salve;
    private List<Misterio> misterios;
    private int day;

    /*
      @TODO
      - Arreglar esto de otro modo
     */
    public String getByDay() {
        switch (day) {
            case 1:
                return "Misterios Gloriosos";
            case 2:
                return "Misterios Gozosos";
            case 3:
                return "Misterios Dolorosos";
            case 4:
                return "Misterios Luminosos";
            default:
                return "*";
        }
    }

    @SuppressWarnings("unused")
    public void setMisterios(List<Misterio> misterios) {
        this.misterios = misterios;
    }

    public Spanned getSaludo() {
        return Utils.fromHtml(saludo);
    }

    @SuppressWarnings("unused")
    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }

    public String getPadrenuestro() {
        return padrenuestro;
    }

    @SuppressWarnings("unused")
    public void setPadrenuestro(String padrenuestro) {
        this.padrenuestro = padrenuestro;
    }

    @SuppressWarnings("unused")
    public SpannableStringBuilder misterioCompleto() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.fromHtml(getPadrenuestro()));
        sb.append(LS2);
        for (int i = 0; i < 10; i++) {
            sb.append(Utils.fromHtml(getAvemaria()));
            sb.append(LS2);
        }
        sb.append(Utils.fromHtml(gloria));
        sb.append(LS2);
        return sb;
    }

    public String getAvemaria() {
        return avemaria;
    }

    @SuppressWarnings("unused")
    public void setAvemaria(String avemaria) {
        this.avemaria = avemaria;
    }

    public String getGloria() {
        return gloria;
    }

    public void setGloria(String gloria) {
        this.gloria = gloria;
    }

    public Spanned getLetanias() {
        return Utils.fromHtml(letanias);
    }

    @SuppressWarnings("unused")
    public void setLetanias(String letanias) {
        this.letanias = letanias;
    }

    public Spanned getOracion() {
        return Utils.fromHtml(oracion);
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public Spanned getSalve() {
        return Utils.fromHtml(salve);
    }

    @SuppressWarnings("unused")
    public void setSalve(String salve) {
        this.salve = salve;
    }

    @SuppressWarnings("unused")
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public SpannableStringBuilder getForView(boolean isBrevis) {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.toH3Red("INVOCACIÓN INICIAL"));
        sb.append(LS2);
        sb.append(getSaludo());
        sb.append(LS2);

        if(isBrevis) {
            sb.append(getMisteriosBrevis());
        }else{
            sb.append(getMisterios());
        }

        sb.append(Utils.toH3Red("LETANÍAS DE NUESTRA SEÑORA"));
        sb.append(LS2);

        sb.append(getLetanias());

        sb.append(LS2);
        sb.append(Utils.toH3Red("SALVE"));
        sb.append(LS2);

        sb.append(getSalve());

        sb.append(LS2);
        sb.append(Utils.toH3Red("ORACIÓN"));
        sb.append(LS2);

        sb.append(getOracion());
        return sb;
    }

    public SpannableStringBuilder getMisterios() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        Misterio m = misterios.get(this.day - 1);
        sb.append(LS);
        sb.append(Utils.toH2Red(m.getTitulo()));
        sb.append(LS2);
        int x = 0;
        for (String s : m.getContenido()) {
            sb.append(Utils.toH4Red(m.ordinalName.get(x)));
            sb.append(LS);
            sb.append(Utils.toH3Red(s));
            sb.append(LS2);
            sb.append(Utils.fromHtml(getPadrenuestro()));
            sb.append(LS2);

            for (int i = 0; i < 10; i++) {
                sb.append(Utils.toRed(String.format(Locale.getDefault(), "%d" +
                        "%s",i+1,".-")));
                sb.append(LS);
                sb.append(Utils.fromHtml(getAvemaria()));
                sb.append(LS2);
            }
            sb.append(LS);
            sb.append(Utils.fromHtml(gloria));
            sb.append(LS2);
            sb.append(LS);
            x++;
        }
        return sb;
    }

    public SpannableStringBuilder getMisteriosBrevis() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        Misterio m = misterios.get(this.day - 1);
        sb.append(LS);
        sb.append(Utils.toH2Red(m.getTitulo()));
        sb.append(LS2);
        int x = 0;
        for (String s : m.getContenido()) {
            sb.append(Utils.toH4Red(m.ordinalName.get(x)));
            sb.append(LS);
            sb.append(Utils.toH3Red(s));
            sb.append(LS2);
            sb.append("Padre Nuestro ...");
            sb.append(LS2);
            sb.append("10 Ave María");
            sb.append(LS2);
            sb.append("Gloria ...");
            sb.append(LS2);
            sb.append(LS);
            x++;
        }
        return sb;
    }
    public SpannableStringBuilder getForRead() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getTitleForRead());
        sb.append(getSaludo());
        sb.append(getMisteriosForRead());
        sb.append("LETANÍAS DE NUESTRA SEÑORA.");
        sb.append(getLetanias());
        sb.append("SALVE.");
        sb.append(getSalve());
        sb.append("ORACIÓN.");
        sb.append(getOracion());
        return sb;
    }

    public StringBuilder getMisteriosForRead() {
        StringBuilder sb = new StringBuilder();
        Misterio m = misterios.get(this.day - 1);
        sb.append(m.getTitulo());
        sb.append(".");
        int x = 0;
        for (String s : m.getContenido()) {
            sb.append(m.ordinalName.get(x));
            sb.append(".");
            sb.append(s);
            sb.append(".");
            sb.append(Utils.fromHtml(getPadrenuestro()));
            sb.append(".");

            for (int i = 0; i < 10; i++) {
                sb.append(Utils.fromHtml(getAvemaria()));
            }
            sb.append(Utils.fromHtml(gloria));
            x++;
        }
        return sb;
    }

    private String getTitleForRead() {
        return "Santo Rosario.";
    }

    public String getSubTitle() {
        return getByDay();
    }
}