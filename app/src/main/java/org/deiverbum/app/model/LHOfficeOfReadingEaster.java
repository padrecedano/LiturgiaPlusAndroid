package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_OFFICE_OF_READING;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.Collections;
import java.util.List;

public class LHOfficeOfReadingEaster extends LHOfficeOfReading {
    @SuppressWarnings("unused")
    private String metaInfo;
    private List<LHOfficeBiblicalEaster> biblical;
    public LHPsalmody lhPsalmody;

    public LHOfficeOfReadingEaster() {
    }

    @SuppressWarnings("unused")
    public String getMetaInfo() {
        return metaInfo;
    }

    @SuppressWarnings("unused")
    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    public String getMetaInfoForView() {
        return
                String.format("%s%s%s%s%s", "Hoy, la celebración solemne de la Vigilia pascual reemplaza el Oficio de lectura.", LS2, "Quienes no hayan participado en la celebración de la Vigilia pascual usarán, para el Oficio de lectura, al menos cuatro de las lecturas de la referida Vigilia pascual, con sus cantos y oraciones. Es muy conveniente elegir, de entre las lecturas de la Vigilia pascual, las que se proponen a continuación.", LS2, "Este Oficio empieza directamente con las lecturas.");
    }

    public String getMetaInfoForRead() {
        return "Hoy, la celebración solemne de la Vigilia pascual reemplaza el Oficio de lectura. Quienes no hayan participado en la celebración de la Vigilia pascual usarán, para el Oficio de lectura, al menos cuatro de las lecturas de la referida Vigilia pascual, con sus cantos y oraciones. Es muy conveniente elegir, de entre las lecturas de la Vigilia pascual, las que se proponen a continuación. Este Oficio empieza directamente con las lecturas.";
    }

    @SuppressWarnings("unused")
    public List<LHOfficeBiblicalEaster> getBiblical() {
        return biblical;
    }

    @SuppressWarnings("unused")
    public void setBiblicalE(List<LHOfficeBiblicalEaster> biblical) {
        this.biblical = biblical;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatSubTitle(Utils.toLower(TITLE_OFFICE_OF_READING));
    }

    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_OFFICE_OF_READING);
    }

    @Override
    public SpannableStringBuilder getAll(int calendarTime) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.toSmallSizeRed(getMetaInfoForView()));
        sb.append(getAllBiblica(calendarTime));
        return sb;
    }

    public SpannableStringBuilder getAllBiblica(int calendarTime) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        int i = 0;
        for (LHOfficeBiblicalEaster oneBiblica : this.biblical) {
            sb.append(LS2);
            sb.append(oneBiblica.getBiblical());
            sb.append(LS2);
            sb.append(lhPsalmody.getSalmosByIndex(i));
            sb.append(LS2);
            sb.append(oneBiblica.getPrayer().getAll());
            sb.append(LS2);
        }
        return sb;
    }

    public SpannableStringBuilder getAllBiblicaForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        int i = 0;
        for (LHOfficeBiblicalEaster oneBiblica : this.biblical) {
            sb.append(oneBiblica.getBiblicalForRead());
            sb.append(lhPsalmody.getSalmosByIndexForRead(i));
            sb.append(oneBiblica.getPrayer().getAllForRead());
        }
        return sb;
    }

    @Override
    public String getAllForRead() {
        return
                getMetaInfoForRead() +
                        getAllBiblicaForRead();
    }

    public void sort() {
        Collections.sort(this.biblical);
    }
}