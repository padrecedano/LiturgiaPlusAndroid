package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_INTERCESSIONS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.util.Locale;

public class LHIntercession {
    public Integer intercessionID;
    public String intro;
    public String intercession;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public SpannableStringBuilder getAll() {

        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        String[] introArray = intro.split("\\|");
        if (introArray.length == 3) {
            sb.append(introArray[0]);
            sb.append(LS2);
            sb.append(Utils.fromHtml(String.format(new Locale("es"),"<i>%s</i>",introArray[1])));
            sb.append(LS2);
            sb.append(getIntercession());
            sb.append(LS2);
            sb.append(introArray[2]);
        } else {
            sb.append(getIntro());
            sb.append(LS2);
            sb.append(getIntercession());
        }
        return sb;

    }

    public Spanned getIntercession() {
        return Utils.fromHtml(Utils.getFormato(intercession));
    }

    public void setIntercession(String intercession) {
        this.intercession = intercession;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle(TITLE_INTERCESSIONS);
    }

    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_INTERCESSIONS);
    }

    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(LS2);
        String[] introArray = intro.split("\\|");
        if (introArray.length == 3) {
            sb.append(introArray[0]);
            sb.append(introArray[1]);
            sb.append(getIntercession());
            sb.append(introArray[2]);
        } else {
            sb.append(getIntro());
            sb.append(getIntercession());
        }
        return sb;
    }
}