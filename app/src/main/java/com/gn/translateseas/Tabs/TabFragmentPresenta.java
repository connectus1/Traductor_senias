package com.gn.translateseas.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gn.translateseas.Introduccion;
import com.gn.translateseas.databinding.TabInicioBinding;

public class TabFragmentPresenta extends Fragment {
    private TabInicioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TabInicioBinding.inflate(inflater, container, false);

        binding.imgNextTab.setOnClickListener(view ->{
            Introduccion.next();
        });

        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
