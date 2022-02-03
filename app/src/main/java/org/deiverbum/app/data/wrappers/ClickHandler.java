package org.deiverbum.app.data.wrappers;

import static org.deiverbum.app.utils.Constants.ERR_SUBJECT;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import org.deiverbum.app.utils.Configuration;
import org.deiverbum.app.utils.Constants;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01
 *
 * Fecha:  15/1/22
 */
public class ClickHandler {

private Context context;
private View v;

    public ClickHandler(Context context) {
        this.context=context;
    }

    public void onClickMail(View v,CustomException data) {
        //this.v=v;
        //Log.d("aa",data.getTrace()+v.getParent());
        //this.context=v.getContext();
        String msg=String.format("Error: %s\n\nLugar: %s",data.getTrace(),"TAG");
        String subject= String.format("Reporte de error Liturgia+ v. %d", Constants.VERSION_CODE);

        composeEmail(new String[]{Configuration.MY_EMAIL},  ERR_SUBJECT, data.getTrace());

        //
    }

    public void composeEmail(String[] addresses, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        //context.startActivity(intent);
        Log.d("a",context.getClass().getName());
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);

        }
    }
}
