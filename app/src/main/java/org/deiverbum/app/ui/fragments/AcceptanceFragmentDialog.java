package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.FILE_PRIVACY;
import static org.deiverbum.app.utils.Constants.FILE_TERMS;
import static org.deiverbum.app.utils.Constants.PREF_ACCEPT;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentAcceptanceBinding;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.FileViewModel;
import org.deiverbum.app.workers.TodayWorker;

import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * <p>Fragmento que muestra el diálogo inicial para la Aceptción de la
 * Política de Privacidad y los Términos y Condiciones de Uso.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

@AndroidEntryPoint
public class AcceptanceFragmentDialog extends DialogFragment {

    public static final String TAG = "AcceptanceFragmentDialog";
    private FragmentAcceptanceBinding binding;

    private FileViewModel mViewModel;
    private TextView textPrivacy;
    private TextView textTerms;

    public static void display(FragmentManager fragmentManager) {
        AcceptanceFragmentDialog acceptanceFragmentDialog = new AcceptanceFragmentDialog();
        acceptanceFragmentDialog.setCancelable(false);
        acceptanceFragmentDialog.show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FileViewModel.class);
        binding = FragmentAcceptanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareView();
        observeData();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void prepareView() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        float fontSize = Float.parseFloat(sp.getString("font_size", "18"));
        TextView textInitial = binding.textInitial;
        textPrivacy = binding.textPrivacy;
        textTerms = binding.textTerms;
        TextView textFinal = binding.textFinal;
        //TextView textContacto = binding.textContacto;
        textInitial.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textPrivacy.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textTerms.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textFinal.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        //textContacto.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        Button button = binding.btnEmail;

        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.toH2Red("Aceptación"));
        sb.append("\n\nSi aceptas tanto la Política de Privacidad, como los " +
                "Términos y Condiciones de Uso, pulsa en el botón Aceptar. " +
                "Accederás a la " +
                "pantalla inicial de la aplicación y el " +
                "estado de tu aceptación se guardará en el dispositivo. " +
                "Será revocado si desinstalas la Aplicación o si desmarcas el" +
                " botón de aceptación en algún momento.");
        textFinal.setText(sb, TextView.BufferType.SPANNABLE);

        button.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(PREF_ACCEPT, true);
            editor.apply();
            //launchWorker();
            dismiss();
        });
    }

    private void observeData() {
        mViewModel.getBook(FILE_PRIVACY).observe(this,
                data -> {
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        Book book = data.getData();
                        textPrivacy.setText(book.getForView(), TextView.BufferType.SPANNABLE);
                    } else {
                        textPrivacy.setText(Utils.fromHtml(data.getError()));
                    }
                });

        mViewModel.getBook(FILE_TERMS).observe(this,
                data -> {
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        Book book = data.getData();
                        textTerms.setText(book.getForView(),
                                TextView.BufferType.SPANNABLE);
                    } else {
                        textTerms.setText(Utils.fromHtml(data.getError()));
                    }
                });
    }
    public void launchWorker() {
        WorkManager mWorkManager = WorkManager.getInstance(getActivity().getApplicationContext());

        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(TodayWorker.class, 15, TimeUnit.MINUTES)
                        .addTag("TAG_SYNC_DATA")
                        .setConstraints(constraints)
                        //.setInputData(inputData)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        mWorkManager.enqueueUniquePeriodicWork(
                "SYNC_TODAY",
                ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work
                // policy
                periodicSyncDataWork //work request
        );
        mWorkManager.getWorkInfoByIdLiveData(periodicSyncDataWork.getId()).observe(this,
                workInfo -> {
                    //mWorkManager.cancelWorkById(workInfo.getId());
                });
    }

}
