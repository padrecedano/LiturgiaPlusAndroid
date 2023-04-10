package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;
import static org.deiverbum.app.utils.Utils.normalizeEnd;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class MassReading extends Biblical implements Comparable<MassReading> {
    private String tema;

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTemaForRead() {
        return normalizeEnd(tema);
    }

    /**
     * <p>Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.</p>
     *
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    public SpannableStringBuilder getAll(int type) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(LS);
        sb.append(Utils.formatTitle(getHeader(type)));
        sb.append(LS2);
        sb.append(book.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCita()));
        sb.append(LS2);
        if (tema != null) {
            sb.append(Utils.toRed(getTema()));
            sb.append(LS2);
        }
        sb.append(getTextoSpan());
        sb.append(Utils.LS2);
        return sb;
    }


    /**
     * <p>Obtiene la lectura bíblica completa formateada para la lectura de voz.</p>
     *
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     * @since 2022.01
     */
    @Override
    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.normalizeEnd(findOrden(getOrden())));
        sb.append(getLibroForRead());
        sb.append(getTemaForRead());
        sb.append(getTextoForRead());
        return sb;
    }

    public String getHeader(int type) {
        if (type == 0) {
            String header = "";
            if (this.order >= 1 && this.order <= 19) {
                header = "PRIMERA LECTURA";
            }

            if (this.order >= 20 && this.order <= 29) {
                header = "SALMO RESPONSORIAL";
            }

            if (this.order >= 30 && this.order <= 39) {
                header = "SEGUNDA LECTURA";
            }
            if (this.order >= 40) {
                header = "EVANGELIO";
            }

            return header;
        } else {
            return getHeaderByType(type);

        }
    }

    /**
     * <p>Obtiene el encabezado de cada lectura según el tipo.</p>
     *
     * @return Un objeto {@link String con el contenido.}
     * @since 2023.1.2
     */
    public String getHeaderByType(int type) {
        /*
            type 1 es la Vigilia Pascual, con el siguiente esquema:
            1. 1ª Lectura
            2. Salmo
            3. O bien: Salmo
            4. 1ª Lectura
            5. Salmo
            6. 3ª Lectura
            7. Salmo
            8. 4ª Lectura
            9. Salmo
            10. 5ª Lectura
            11. Salmo
            12. 6ª Lectura
            13. Salmo
            14. 7ª Lectura
            15. Salmo
            16. o bien: Salmo
            17. Epístola
            18. Salmo
            40. Evangelio
         */
        String header = "";
        if (type == 1) {
            if (this.order == 1) {
                header = "PRIMERA LECTURA";
            }
            if (this.order == 2 || this.order == 5 || this.order == 7 ||
                    this.order == 9 || this.order == 11 || this.order == 13 || this.order == 15 || this.order == 18) {
                header = "SALMO RESPONSORIAL";
            }
            if (this.order == 3 || this.order == 16) {
                header = "O bien: SALMO RESPONSORIAL";
            }
            if (this.order == 4) {
                header = "SEGUNDA LECTURA";
            }
            if (this.order == 6) {
                header = "TERCERA LECTURA";
            }
            if (this.order == 8) {
                header = "CUARTA LECTURA";
            }
            if (this.order == 10) {
                header = "QUINTA LECTURA";
            }
            if (this.order == 12) {
                header = "SEXTA LECTURA";
            }
            if (this.order == 14) {
                header = "SÉPTIMA LECTURA";
            }
            if (this.order == 17) {
                header = "EPÍSTOLA";
            }
            if (this.order >= 40) {
                header = "EVANGELIO";
            }
        }
        return header;
    }

    public String findOrden(int orden) {
        String orderText;
        if (orden <= 19) {
            orderText = "Primera Lectura";
        } else if (orden <= 29) {
            orderText = "Salmo Responsorial";
        } else if (orden <= 39) {
            orderText = "Segunda Lectura";
        } else {
            orderText = "Evangelio";
        }
        return orderText;
    }


    @Override
    public int compareTo(MassReading e) {
        return this.getOrden().compareTo(e.getOrden());
    }
}