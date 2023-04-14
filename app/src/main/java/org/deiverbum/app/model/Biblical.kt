package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

import java.util.Locale;

@SuppressWarnings("SameReturnValue")
public class Biblical {
    public Integer bookFK = 0;
    @Ignore
    protected BibleBook book;
    protected String verseChapter;
    protected String text;
    protected String quote;
    @Ignore
    protected Integer order;
    protected Integer readingID;
    private String verseFrom;
    private String verseTo;

    public BibleBook getLibro() {
        return book;
    }

    public void setLibro(BibleBook libro) {
        this.book = libro;
    }

    public String getLibroForRead() {
        return book.getForRead();
    }

    public String getCapitulo() {
        return verseChapter;
    }

    public void setCapitulo(String capitulo) {
        this.verseChapter = capitulo;
    }

    public String getVersoInicial() {
        return verseFrom;
    }

    public void setVersoInicial(String versoInicial) {
        this.verseFrom = versoInicial;
    }

    public String getDesde() {
        return verseFrom;
    }

    public void setDesde(String versoInicial) {
        this.verseFrom = versoInicial;
    }

    public String getVersoFinal() {
        return verseTo;
    }

    public void setVersoFinal(String versoFinal) {
        this.verseTo = versoFinal;
    }

    public String getHasta() {
        return verseTo;
    }

    public void setHasta(String verseTo) {
        this.verseTo = verseTo;
    }

    public BibleBook getBook() {
        return book;
    }

    public void setBook(BibleBook book) {
        this.book = book;
    }

    public String getVerseChapter() {
        return verseChapter;
    }

    public void setVerseChapter(String chapter) {
        this.verseChapter = chapter;
    }

    public String getVerseFrom() {
        return verseFrom;
    }

    public void setVerseFrom(String verseFrom) {
        this.verseFrom = verseFrom;
    }

    public String getVerseTo() {
        return verseTo;
    }

    public void setVerseTo(String verseTo) {
        this.verseTo = verseTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getReadingID() {
        return readingID;
    }

    public void setReadingID(Integer readingID) {
        this.readingID = readingID;
    }

    public Integer getBookFK() {
        return bookFK;
    }

    public void setBookFK(Integer bookFK) {
        this.bookFK = bookFK;
    }

    public Spanned getTextoSpan() {
        return Utils.fromHtml(text);
    }


    public Spanned getTextoForRead() {
        return Utils.fromHtml(Utils.getFormatoForRead(text));
    }

    public String getTexto() {

        return text;
    }

    public void setTexto(String texto) {
        this.text = texto;
    }

    public String getCita() {
        return quote;
        //getReferencia();
    }

    public void setCita(String ref) {
        this.quote = ref;
    }

    public String getRefBreve() {
        return (this.quote != null) ? this.quote : "";
    }

    @SuppressWarnings("unused")
    public String getReferencia() {
        return String.format(new Locale("es"), "%s, %s%s", getCapitulo(), getVersoInicial(), getVersoFinal());
    }

    public String getHeader() {
        return "PRIMERA LECTURA";
    }

    public String getConclusionForRead() {
        return "Palabra de Dios.";
    }

    public String getResponsorioHeaderForRead() {
        return "LHResponsoryShort.";
    }

    /**
     * <p>Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.</p>
     *
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(book.getLiturgyName());
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
     *
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.pointAtEnd(getHeader()));
        sb.append(getLibroForRead());
        sb.append(getTextoForRead());
        sb.append(getConclusionForRead());
        sb.append(getResponsorioHeaderForRead());
        return sb;
    }

    public Integer getOrden() {
        return this.order;
    }

    public void setOrden(Integer orden) {
        this.order = orden;
    }

    public Integer getLecturaId() {
        return this.readingID;
    }


}
