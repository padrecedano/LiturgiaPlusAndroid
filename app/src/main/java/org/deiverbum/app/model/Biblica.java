package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Biblica {
    private String libro;
    private String capitulo;
    private String versoInicial;
    private String versoFinal;
    private String tema;
    private String texto;
    private String ref;
    private Responsorio responsorio;

    public String getLibro() {
        return libro;
    }

    public String getLibroForRead() {
        return libro+".";
    }


    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(String capitulo) {
        this.capitulo = capitulo;
    }

    public String getVersoInicial() {
        return versoInicial;
    }
    //@PropertyName("v_Inicial")

    public void setVersoInicial(String versoInicial) {
        this.versoInicial = versoInicial;
    }

    public String getVersoFinal() {
        return versoFinal;
    }
    //@PropertyName("v_Final")

    public void setVersoFinal(String versoFinal) {
        this.versoFinal = versoFinal;
    }

    public String getTema() {
        return tema;
    }
    public String getTemaForRead() {
        return tema+".";
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Spanned getTextoSpan() {
        return Utils.fromHtml(Utils.getFormato(texto));
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {


        return texto;
    }


    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Responsorio getResponsorio() {
        return responsorio;
    }

    public void setResponsorio(Responsorio responsorio) {
        this.responsorio=responsorio;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle("PRIMERA LECTURA");
    }

    public String getHeaderForRead() {
        return "Primera lectura.";
    }

    public String getConclusionForRead() {
        return "Palabra de Dios.";
    }

    public SpannableStringBuilder getResponsorioHeader() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.toRed(String.format("%-15s", "Responsorio")));
        sb.append(Utils.toRed(getRef()));
        return sb;

    }

    public String getResponsorioHeaderForRead() {
        return "Responsorio.";
    }

    /**
     * <p>Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(getLibro());
        sb.append("    ");
        sb.append(Utils.toRed(getCapitulo()));
        sb.append(", ");
        sb.append(Utils.toRed(getVersoInicial()));
        sb.append(Utils.toRed(getVersoFinal()));
        sb.append(LS2);
        sb.append(Utils.toRed(getTema()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        sb.append(responsorio.getAll());
        return sb;
    }

    /**
     * <p>Obtiene la lectura bíblica completa formateada para la lectura de voz.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    public SpannableStringBuilder getAllForRead(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getLibroForRead());
        sb.append(getTemaForRead());
        sb.append(getTexto());
        sb.append(getConclusionForRead());
        sb.append(getResponsorioHeaderForRead());
        sb.append(getResponsorio().getAllForRead());
        return sb;
    }


}
