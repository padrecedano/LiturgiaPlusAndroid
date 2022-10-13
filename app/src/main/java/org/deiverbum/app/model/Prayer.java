package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_PRAYER;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Prayer {
    public Integer prayerID;
    public Integer order;

    public String prayer;

    public String getPrayer() {
        return prayer;
    }

    public void setPrayer(String prayer) {
        this.prayer = prayer;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle(TITLE_PRAYER);
    }

    public final Spanned getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
            sb.append(getHeader());
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(prayer));

        return sb;
    }

    public String getAllForRead() {
        return Utils.pointAtEnd(TITLE_PRAYER) +
                Utils.fromHtml(prayer);
    }
}