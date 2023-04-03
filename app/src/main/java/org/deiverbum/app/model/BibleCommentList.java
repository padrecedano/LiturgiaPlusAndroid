package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_BIBLE_COMMENTS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import com.google.firebase.firestore.PropertyName;

import org.deiverbum.app.utils.ColorUtils;
import org.deiverbum.app.utils.Utils;

import java.util.List;

public class BibleCommentList {
    public String padre;
    @SuppressWarnings("unused")
    public int id_homilia;
    public String pericopa;
    private Today today;
    public List<List<BibleComment>> allComentarios;
    private MassReading biblica;

    @PropertyName("comentarios")
    public List<BibleComment> comentarios;

    public BibleCommentList() {
    }


    @PropertyName("comentarios")
    public void setComentarios(List<BibleComment> comentarios) {
        this.comentarios = comentarios;
    }

    public void setAllComentarios(List<List<BibleComment>> comentarios) {
        this.allComentarios = comentarios;
    }

    @PropertyName("comentarios")
    public List<BibleComment> getComentarios() {
        return this.comentarios;
    }

    private SpannableStringBuilder getTitulo() {
        return Utils.toH3Red(TITLE_BIBLE_COMMENTS);
    }

    private String getTituloForRead() {
        return Utils.pointAtEnd(TITLE_BIBLE_COMMENTS);
    }


    public SpannableStringBuilder getAllForView(boolean nightMode) {
        ColorUtils.isNightMode=nightMode;
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            sb.append(today.getSingleForView());
            sb.append(LS2);
            sb.append(getTitulo());
            sb.append(LS2);
            for (List<BibleComment> subList : allComentarios) {
                if (subList.size() > 0) {
                    int x = 1;
                    for (BibleComment item : subList) {
                        if (x++ == 1) {
                            sb.append(item.getBiblica().getAll());
                            sb.append(LS2);
                            sb.append(Utils.formatTitle(TITLE_BIBLE_COMMENTS));
                            sb.append(LS2);
                        }
                        sb.append(item.getAllForView());
                    }
                }
            }
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public StringBuilder getAllForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(today.getSingleForRead());
            sb.append(getTituloForRead());
            for (List<BibleComment> subList : allComentarios) {
                if (subList.size() > 0) {
                    int x = 1;
                    for (BibleComment item : subList) {
                        if (x++ == 1) {
                            sb.append(item.getBiblica().getAllForRead());
                            sb.append(Utils.pointAtEnd(TITLE_BIBLE_COMMENTS));
                        }
                        sb.append(item.getAllForRead());
                    }
                }
            }
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));

        }
        return sb;
    }


    @SuppressWarnings("unused")
    public void setPericopa(String pericopa) {
        this.pericopa = pericopa;
    }

    @SuppressWarnings("unused")
    public String getPericopa() {
        return this.pericopa;
    }

    public void setHoy(Today today) {
        this.today = today;
    }

    public MassReading getBiblica() {
        return biblica;
    }

    public void setBiblica(MassReading biblica) {
        this.biblica = biblica;
    }

}
