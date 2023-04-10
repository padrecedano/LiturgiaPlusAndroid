package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.NBSP_4;
import static org.deiverbum.app.utils.Utils.LS2;
import static org.deiverbum.app.utils.Utils.toH3Red;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

import java.util.List;
import java.util.Locale;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class Content {

    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("text")
    @Expose
    private List<String> text = null;
    @SerializedName("title")
    @Expose
    private String title;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public SpannableStringBuilder getByType() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if (type == 10) {
            Spanned txt = Utils.fromHtml(String.format(new Locale("es"), "%s<b>%s</b> %s",
                    NBSP_4, item,
                    getTextForView()));
            sb.append(txt);
            sb.append(LS2);
        } else if (type == 2) {
            sb.append(Utils.toH3(title));
            sb.append(LS2);

            sb.append(getTextForView());
        } else if (type == 3) {
            sb.append(Utils.toH4(title));
            sb.append(LS2);
            sb.append(getTextForView());

        } else if (type == 4) {
            sb.append(Utils.toH3Red(title));
            sb.append(LS2);
            //sb.append(getTextForView());
        } else if (type == 5) {
            sb.append(Utils.toH4Red(title));
            sb.append(LS2);
        } else if (type == 11) {
            sb.append(getTextForView());
        } else if (type == 12) {
            sb.append(getTextForView());
        } else if (type == 13) {
            sb.append(getNumberedList());
        } else {
            sb.append(getTextForView());
        }
        return sb;
    }

    private SpannableStringBuilder getNumberedList() {

        SpannableStringBuilder sb = new SpannableStringBuilder();
        int i = 1;
        for (String s : text) {
            String tmp = String.format(new Locale("es"), "\t\t%d. %s", i,
                    s);
            sb.append(Utils.fromHtml(tmp));
            sb.append(LS2);
            i++;
        }
        return sb;
    }


    private SpannableStringBuilder getTextForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (String s : text) {
            if (type == 11) {
                sb.append("\t\t\t\t");
                sb.append("- ");
            }
            if (type < 4) {
                sb.append("\t\t");
            }

            if (type == 20) {
                sb.append(toH3Red(getTitle()));
                sb.append(LS2);
            }
            sb.append(Utils.fromHtml(s));
            sb.append(LS2);
        }
        return sb;
    }

    public SpannableStringBuilder getHtmlByType() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if (type == 10) {
            String txt = String.format(new Locale("es"), "%s<b>%s</b> %s",
                    NBSP_4, item,
                    getTextHtml());
            sb.append(txt);
            sb.append(LS2);
        } else if (type == 2) {
            sb.append(Utils.toH3(title));
            sb.append(LS2);

            sb.append(getTextForView());
        } else if (type == 3) {
            sb.append(Utils.toH4(title));
            sb.append(LS2);
            sb.append(getTextHtml());
        } else if (type == 11) {
            sb.append(getTextHtml());
        } else if (type == 12) {
            sb.append(getTextHtml());
        } else if (type == 13) {
            sb.append(getNumberedList());
        } else {
            sb.append(getTextHtml());
        }
        return sb;
    }

    private SpannableStringBuilder getTextHtml() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (String s : text) {
            if (type == 11) {
                sb.append("\t\t\t\t");
                sb.append("- ");
            }
            if (type < 4) {
                sb.append("\t\t");
            }

            if (type == 20) {
                sb.append(toH3Red(getTitle()));
                sb.append(LS2);
            }
            sb.append(s);
            sb.append(LS2);
        }
        return sb;
    }

}
