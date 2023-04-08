package com.gn.translateseas.Tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.gn.translateseas.databinding.TabInicioBinding;

public class TabFragmentPresenta extends Fragment {
    private TabInicioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TabInicioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
