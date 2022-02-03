package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

/*
Sustituida por {@link Lecturas}
 */
@Deprecated
public class LiturgiaPalabra {

    private int tipo;
    private List<Lectura> lecturas;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTipos() {
        return "tipo";
    }

    public List<Lectura> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<Lectura> lecturas) {
        this.lecturas = lecturas;
    }

    public SpannableStringBuilder getEvangelio() {
        int tipo = this.tipo;
        String txtLectura = "";
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (Lectura l : lecturas) {
            if (l.getOrden() == 40) {
                sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getRef()));
                sb.append(Utils.LS2);
                sb.append(Utils.toRed(l.getTema()));
                sb.append(Utils.LS2);
                txtLectura = Utils.getFormato(l.getTexto());
                sb.append(Utils.fromHtml(txtLectura));
                sb.append(Utils.LS2);
            }
        }

        return sb;
    }


    public SpannableStringBuilder getLiturgiaPalabra() {
        String txtLectura = "";
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (Lectura l : lecturas) {
            sb.append(Utils.LS2);
            sb.append(Utils.toH3Red(findOrden(l.getOrden())));
            sb.append(Utils.LS2);
            sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getRef()));
            sb.append(Utils.LS2);
            sb.append(Utils.toRed(l.getTema()));
            sb.append(Utils.LS2);
            txtLectura = Utils.getFormato(l.getTexto());
            sb.append(Utils.fromHtml(txtLectura));
            sb.append(Utils.LS2);
        }

        return sb;
    }

    public SpannableStringBuilder getLiturgiaPalabraforRead() {
        String txtLectura = "";
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (Lectura l : lecturas) {
            sb.append(findOrden(l.getOrden()));
            sb.append(Utils.LS2);
            sb.append(l.getLibro());
            sb.append(Utils.LS2);
            sb.append(Utils.LS2);
            sb.append(l.getTema());
            sb.append(Utils.LS2);
            txtLectura = l.getTexto();
            sb.append(Utils.fromHtml(txtLectura));
            sb.append(Utils.LS2);
        }

        return sb;
    }

    /*Bug fixed on v. 2020.1.3*/
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
/*
        HashMap<Integer, String> orderMap = new HashMap<Integer, String>();
        orderMap.put(1, "Primera Lectura");
        orderMap.put(10, "Primera Lectura");
        orderMap.put(2, "Salmo Responsorial");
        orderMap.put(20, "Salmo Responsorial");
        orderMap.put(3, "Segunda Lectura");
        orderMap.put(30, "Segunda Lectura");
        orderMap.put(4, "Evangelio");
        orderMap.put(40, "Evangelio");
        orderMap.put(41, "Evangelio");
        orderMap.put(42, "Evangelio");
        orderMap.put(44, "Evangelio");
        orderMap.put(45, "Evangelio");
        orderMap.put(46, "Evangelio");

       // String orderText = orderMap.get(orden);
*/
        return orderText;


    }

}
