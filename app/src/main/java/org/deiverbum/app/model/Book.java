package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.ColorUtils;
import org.deiverbum.app.utils.Utils;

import java.util.List;
import java.util.Locale;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */


public class Book {

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("intro")
    @Expose
    private Intro intro;

    @SerializedName("bookType")
    @Expose
    private int bookType;

    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = null;

    @SerializedName("agreeYes")
    @Expose
    private String agreeYes;

    @SerializedName("agreeNot")
    @Expose
    private String agreeNot;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBookType() {
        return bookType;
    }

    public void setBookType(int bookType) {
        this.bookType = bookType;
    }

    public Intro getIntro() {
        return intro;
    }

    public void setIntro(Intro intro) {
        this.intro = intro;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getAgreeYes() {
        return this.agreeYes;
    }

    public String getAgreeNot() {
        return this.agreeNot;
    }

    @SuppressWarnings("unused")
    public void setAgreeYes(String agreeYes) {
         this.agreeYes=agreeYes;
    }

    @SuppressWarnings("unused")
    public void setAgreeNot(String agreeNot) {
         this.agreeNot=agreeNot;
    }

    public SpannableStringBuilder getForView(boolean isNightMode){
        ColorUtils.isNightMode=isNightMode;
        SpannableStringBuilder sb=new SpannableStringBuilder();

        if(bookType==2){
            for (Chapter c : chapters) {
                sb.append(c.getAllForView(bookType));
            }

        }else {
            sb.append(Utils.toH2(title));
            sb.append(LS2);
            sb.append("Fecha efectiva: ");
            String dateString = String.format(new Locale("es"),"<b>%s</b>", Utils.getTitleDate(date));
            sb.append(Utils.fromHtml(dateString));
            //sb.append(_abstract.getAllForView());
            sb.append(LS2);
            if (intro != null) {
                for (Content ci : intro.getContent()) {
                    sb.append(ci.getByType());
                }
                sb.append(LS2);

            }

            for (Chapter c : chapters) {
                sb.append(c.getAllForView(bookType));
            }

        }
        //saveHtmlFile(sb.toString());
        return sb;
    }


    @SuppressWarnings("unused")
    public SpannableStringBuilder getForHtml(){
        SpannableStringBuilder sb=new SpannableStringBuilder();

        if(bookType==2){
            for (Chapter c : chapters) {
                sb.append(c.getAllForHtml(bookType));
            }

        }else {
            sb.append(Utils.toH2(title));
            sb.append(LS2);
            sb.append("Fecha efectiva: ");
            String dateString = String.format(new Locale("es"),"<b>%s</b>", Utils.getTitleDate(date));
            sb.append(dateString);
            //sb.append(_abstract.getAllForView());
            sb.append(LS2);
            if (intro != null) {
                for (Content ci : intro.getContent()) {
                    sb.append(ci.getHtmlByType());
                }
                sb.append(LS2);

            }

            for (Chapter c : chapters) {
                sb.append(c.getAllForHtml(bookType));
            }
        }
        return sb;
    }
}


