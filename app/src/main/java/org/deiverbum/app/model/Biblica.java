package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

public class Biblica {
    @Ignore
    protected BibliaLibro libro;
    private String capitulo;
    private String desde;
    private String hasta;
    private String texto;
    private String cita;
    @Ignore
    protected Integer orden;

    protected Integer lecturaId;
    public Integer libroFK=0;

    public BibliaLibro getLibro() {
        return libro;
    }

    public String getLibroForRead() {
        return libro.getForRead();
    }

    public void setLibro(BibliaLibro libro) {
        this.libro = libro;
    }

    public String getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(String capitulo) {
        this.capitulo = capitulo;
    }

    public String getVersoInicial() {
        return desde;
    }

    public void setVersoInicial(String versoInicial) {
        this.desde = versoInicial;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String versoInicial) {
        this.desde = versoInicial;
    }

    public String getVersoFinal() {
        return hasta;
    }

    public void setVersoFinal(String versoFinal) {
        this.hasta = hasta;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String versoFinal) {
        this.hasta = hasta;
    }

    public Spanned getTextoSpan() {
        return Utils.fromHtml(Utils.getFormato(texto));
    }


    public Spanned getTextoForRead() {
        return Utils.fromHtml(Utils.getFormato(texto));
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }


    public String getCita() {
        return cita;
        //getReferencia();
    }
    public String getRefBreve() {
        return (this.cita!=null) ? this.cita : "";
    }

    public void setCita(String ref) {
        this.cita = ref;
    }

    public String getReferencia() {
        return String.format("%s, %s%s",getCapitulo(),getVersoInicial(),getVersoFinal());
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
        sb.append(libro.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCapitulo()));
        sb.append(", ");
        sb.append(Utils.toRed(getVersoInicial()));
        sb.append(Utils.toRed(getVersoFinal()));
        sb.append(LS2);
        //sb.append(Utils.toRed(getTema()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        //sb.append(responsorio.getAll());
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
        sb.append(getTexto());
        sb.append(getConclusionForRead());
        sb.append(getResponsorioHeaderForRead());
        return sb;
    }

    public Integer getOrden() {
        return this.orden;
    }
    public Integer getLecturaId() {
        return this.lecturaId;
    }

    public void setOrden(Integer orden) {
        this.orden=orden;
    }

}
