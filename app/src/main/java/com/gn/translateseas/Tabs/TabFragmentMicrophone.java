package com.gn.translateseas.Tabs;

import static android.Manifest.permission.RECORD_AUDIO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.gn.translateseas.MainActivity;
import com.gn.translateseas.databinding.TabMicrophoneBinding;


public class TabFragmentMicrophone extends Fragment {
    private TabMicrophoneBinding binding;
    private int RecordRequestCode = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TabMicrophoneBinding.inflate(inflater, container, false);
        binding.btnFinishTutorial.setOnClickListener(clickFinish);

        return binding.getRoot();
    }


    private View.OnClickListener clickFinish = View -> {
        if (checkPermission()) {
            saveTutorial();

            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);

            getActivity().finish();
        }
    };

    //Revisa que los permisos para el audio esten permitidos
    private boolean checkPermission() {
        //Verifica que el permiso RECORD_AUDIO este garantizado
        if (ContextCompat.checkSelfPermission(getContext(), RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            String[] permisos = new String[]{RECORD_AUDIO};
            ActivityCompat.requestPermissions(getActivity(), permisos, RecordRequestCode);
            return false;
        }

        return true;
    }

    //Guarda en los SharedPreferences un valor booleano indicando que el usuario ya vio el tutorial
    private void saveTutorial(){
        SharedPreferences preferences = getActivity().getSharedPreferences("translate", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("tutorial", true);
        editor.commit();
    }

}

