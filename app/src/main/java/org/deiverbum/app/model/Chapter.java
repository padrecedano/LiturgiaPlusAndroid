package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class Chapter {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private List<Content> content = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public SpannableStringBuilder getAllForView(int bookType) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if(bookType==10) {
            String chapter = String.format("%s. %s", id, Utils.fromHtml(title));
            sb.append(Utils.toH2RedNew(chapter));
                sb.append(LS2);
        }

        if(bookType==21) {
            sb.append("\t\t");
            sb.append(Utils.toH4Red(String.valueOf(id)));
            sb.append(Utils.toRed(".- "));
        }
        for (Content c : content) {
            sb.append(c.getByType());
        }
            return sb;
        }


    public SpannableStringBuilder getAllForHtml(int bookType) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if(bookType==10) {
            String chapter = String.format("%s. %s", id, title);
            sb.append(Utils.toH2RedNew(chapter));
            sb.append(LS2);
        }

        if(bookType==21) {
            sb.append("\t\t");
            sb.append(Utils.toH4Red(String.valueOf(id)));
            sb.append(Utils.toRed(".- "));
        }
        for (Content c : content) {
            sb.append(c.getHtmlByType());
        }
        return sb;
    }
}